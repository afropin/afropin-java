package com.afropin;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * The AfroPin API client. Signs every request with your key (HMAC-SHA256 over "METHOD.path.timestamp.nonce.sha256(body)"),
 * sends a fresh Idempotency-Key with each one, and returns the response's data: JSON objects as {@code Map}, arrays as
 * {@code List}, lists of records as a {@link Page}. Your key's mode (test or live) decides which records you work with.
 *
 * <pre>
 * AfroPin afropin = new AfroPin("key_id", "secret");
 * Map&lt;String, Object&gt; pin = afropin.reseller().pins().sell(Map.of("catalog_id", "...", "amount", 5000, ...));
 * </pre>
 *
 * Requests are never retried for you: a retried sale with the same external_reference fails safely with
 * duplicate_external_reference, so retry yourself with a new Idempotency-Key when you need to.
 */
public final class AfroPin {
    public static final String VERSION = "2.0.0";
    public static final String BASE_URL = "https://afropin.com/api/v1";

    private final String keyId;
    private final String secret;
    private final String baseUrl;
    private final Duration timeout;
    private final HttpClient http;
    private final Reseller reseller;
    private final Brand brand;

    public AfroPin(String keyId, String secret) {
        this(keyId, secret, BASE_URL, Duration.ofSeconds(30));
    }

    public AfroPin(String keyId, String secret, String baseUrl, Duration timeout) {
        this.keyId = keyId;
        this.secret = secret;
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.timeout = timeout;
        this.http = HttpClient.newBuilder().connectTimeout(timeout).build();
        this.reseller = new Reseller(this);
        this.brand = new Brand(this);
    }

    public Reseller reseller() {
        return reseller;
    }

    public Brand brand() {
        return brand;
    }

    /**
     * Send one request: its data, or a Page for lists. params are the query (GET, DELETE) or the JSON body.
     * idempotencyKey replaces the generated one when not null.
     */
    public Object request(String method, String path, Map<String, ?> params, String idempotencyKey, boolean list) {
        Map<String, ?> values = params == null ? Map.of() : params;
        String url = baseUrl + "/" + path.replaceAll("^/+", "");
        String body = "";
        if (method.equals("GET") || method.equals("DELETE")) {
            StringJoiner query = new StringJoiner("&");
            values.forEach((key, value) -> {
                if (value != null) {
                    query.add(encode(key) + "=" + encode(String.valueOf(value)));
                }
            });
            if (query.length() > 0) {
                url += "?" + query;
            }
        } else {
            body = Json.write(values);
        }

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString();
        String signedPath = URI.create(url).getRawPath().replaceAll("^/+", "");
        String canonical = String.join(".", method, signedPath, timestamp, nonce, sha256Hex(body));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
            .timeout(timeout)
            .method(method, body.isEmpty() ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body))
            .header("X-Key-Id", keyId)
            .header("X-Timestamp", timestamp)
            .header("X-Nonce", nonce)
            .header("X-Signature", hmacHex(secret, canonical))
            .header("Idempotency-Key", idempotencyKey != null ? idempotencyKey : UUID.randomUUID().toString())
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("User-Agent", "afropin-java/" + VERSION)
            .build();

        HttpResponse<String> response;
        try {
            response = http.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new AfroPinException("connection_failed", "Could not reach the AfroPin API: " + e.getMessage(), null, 0);
        }
        Object parsed;
        try {
            parsed = Json.read(response.body());
        } catch (IllegalArgumentException e) {
            throw new AfroPinException("invalid_response", "The API sent a response that is not JSON", null, response.statusCode());
        }
        if (!(parsed instanceof Map) || !Boolean.TRUE.equals(((Map<?, ?>) parsed).get("ok"))) {
            Map<?, ?> error = parsed instanceof Map && ((Map<?, ?>) parsed).get("error") instanceof Map ? (Map<?, ?>) ((Map<?, ?>) parsed).get("error") : Map.of();
            Object code = error.get("code");
            Object message = error.get("message");
            @SuppressWarnings("unchecked")
            Map<String, Object> fields = error.get("fields") instanceof Map ? (Map<String, Object>) error.get("fields") : null;
            throw new AfroPinException(code != null ? code.toString() : "unknown_error", message != null ? message.toString() : "The request failed", fields, response.statusCode());
        }
        Map<?, ?> json = (Map<?, ?>) parsed;
        if (list) {
            @SuppressWarnings("unchecked")
            List<Object> data = json.get("data") instanceof List ? (List<Object>) json.get("data") : List.of();
            @SuppressWarnings("unchecked")
            Map<String, Object> paging = json.get("paging") instanceof Map ? (Map<String, Object>) json.get("paging") : null;
            return new Page(data, paging);
        }
        return json.get("data");
    }

    // A path with its {placeholders} filled in, each value URL-encoded
    static String path(String template, String... values) {
        String path = template;
        for (String value : values) {
            path = path.replaceFirst("\\{[a-z_]+\\}", encode(value).replace("$", "\\$"));
        }
        return path;
    }

    static Map<String, Object> params(Map<String, ?> params) {
        return params == null ? new LinkedHashMap<>() : new LinkedHashMap<>(params);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    static String sha256Hex(String value) {
        try {
            return hex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    static String hmacHex(String key, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return hex(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String hex(byte[] bytes) {
        StringBuilder out = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            out.append(String.format("%02x", b));
        }
        return out.toString();
    }
}
