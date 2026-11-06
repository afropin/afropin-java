package com.afropin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Java SDK's checks, runnable without a test framework: JSON and webhooks offline, then (with AFROPIN_TEST_ENV
 * pointing at a JSON file of test keys, base_url, catalog_id and test_pin) a real round trip in test mode.
 *   javac -d build $(find src -name '*.java') && java -cp build com.afropin.SdkCheck
 */
public final class SdkCheck {
    private static int failed;

    private static void check(String what, boolean ok) {
        System.out.println((ok ? "  ok   " : "  FAIL ") + what);
        failed += ok ? 0 : 1;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        System.out.println("offline");
        Object parsed = Json.read("{\"a\":[1,2.5,\"x\\\"y\",true,null],\"b\":{\"c\":\"\\u00e9\"}}");
        check("reads JSON", ((List<Object>) ((Map<String, Object>) parsed).get("a")).get(2).equals("x\"y") && ((Map<String, Object>) ((Map<String, Object>) parsed).get("b")).get("c").equals("é"));
        check("writes JSON back", Json.read(Json.write(parsed)).equals(parsed));
        String body = "{\"type\":\"pin.sold\",\"data\":{\"pin_id\":\"p1\"}}";
        check("verifies a webhook", Webhooks.verify(body, AfroPin.hmacHex("whsec", body), "whsec"));
        check("refuses a forged webhook", !Webhooks.verify(body, AfroPin.hmacHex("other", body), "whsec"));
        check("encodes path values", AfroPin.path("reseller/pins/{pin_id}", "a/b").equals("reseller/pins/a%2Fb"));

        String envFile = System.getenv("AFROPIN_TEST_ENV");
        if (envFile != null) {
            System.out.println("live (test mode)");
            Map<String, Object> env = (Map<String, Object>) Json.read(Files.readString(Path.of(envFile)));
            Map<String, Object> r = (Map<String, Object>) env.get("reseller");
            Map<String, Object> b = (Map<String, Object>) env.get("brand");
            String base = (String) env.get("base_url");
            AfroPin reseller = new AfroPin((String) r.get("key_id"), (String) r.get("secret"), base, java.time.Duration.ofSeconds(30));
            AfroPin brand = new AfroPin((String) b.get("key_id"), (String) b.get("secret"), base, java.time.Duration.ofSeconds(30));

            check("lists wallets", reseller.reseller().wallets().list().getData().size() > 0);
            Page catalog = reseller.reseller().catalog().list(Map.of("limit", 5));
            check("lists the catalog as a page", catalog.getData().size() > 0 && catalog.getPaging() != null);
            String catalogId = (String) env.get("catalog_id");
            Map<String, Object> product = reseller.reseller().catalog().get(catalogId);
            List<Object> amounts = (List<Object>) product.get("denominations");
            Number amount = amounts != null && !amounts.isEmpty() ? (Number) amounts.get(0) : (Number) product.get("min");
            Map<String, Object> sold = reseller.reseller().pins().sell(Map.of("catalog_id", catalogId, "amount", amount,
                "external_reference", "sdk-java-" + UUID.randomUUID(), "first_name", "Sdk", "last_name", "Test", "email", "sdk.test@gmail.com"));
            check("sells a PIN with its code", sold.get("pin_code") != null && sold.get("pin_id") != null);
            Map<String, Object> pin = reseller.reseller().pins().get((String) sold.get("pin_id"));
            check("reads it back", ((Number) pin.get("balance")).doubleValue() == amount.doubleValue());
            try {
                reseller.reseller().pins().get("00000000-0000-4000-8000-000000000000");
                check("an unknown PIN fails", false);
            } catch (AfroPinException e) {
                check("an unknown PIN fails with pin_not_found (" + e.getStatus() + ")", e.getCode().equals("pin_not_found") && e.getStatus() == 404);
            }
            check("the brand checks a test PIN", brand.brand().pins().check(Map.of("pin_code", env.get("test_pin"))).containsKey("can_redeem"));
        }
        System.out.println(failed == 0 ? "\nall passed" : "\n" + failed + " failed");
        System.exit(failed == 0 ? 0 : 1);
    }
}
