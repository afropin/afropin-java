# AfroPin for Java

The AfroPin gift card API from Java: sell, top up and redeem PINs, quotes, stores and webhooks.

```xml
<dependency>
    <groupId>com.afropin</groupId>
    <artifactId>afropin-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

Java 11 or newer. No dependencies: it uses Java's own HTTP client and a small JSON reader of its own. JSON objects come
back as `Map<String, Object>`, arrays as `List<Object>`, numbers as `Long` or `Double`.

## Start

```java
AfroPin afropin = new AfroPin(System.getenv("AFROPIN_KEY_ID"), System.getenv("AFROPIN_SECRET"));
```

Your API key's mode decides whether you work with test or live records; the address is the same for both. Keep the
secret on your server, never in an app.

### Reseller: sell a PIN

```java
Map<String, Object> pin = afropin.reseller().pins().sell(Map.of(
    "catalog_id", catalogId,          // from afropin.reseller().catalog().list()
    "amount", 5000,
    "external_reference", orderId,    // your own reference; reusing it fails safely
    "first_name", "Ada",
    "last_name", "Obi",
    "email", "ada@example.com"));

String code = (String) pin.get("pin_code");  // returned only here: deliver it to your customer now
```

### Brand: check and redeem a PIN

```java
Map<String, Object> check = afropin.brand().pins().check(Map.of("pin_code", code, "amount", 2500));
if (Boolean.TRUE.equals(check.get("can_redeem"))) {
    afropin.brand().pins().redeem(Map.of("pin_code", code, "amount", 2500, "external_reference", saleId));
}
```

Every call is listed in [METHODS.md](../METHODS.md); cashouts' void is `voidCashout`, as `void` is a Java keyword.
Parameters and response fields are the API's own, as in the developer docs.

## Errors

```java
try {
    afropin.reseller().pins().topUp(Map.of("pin_id", pinId, "amount", 1000, "external_reference", ref));
} catch (AfroPinException e) {
    e.getCode();    // for example pin_not_found, amount_too_large, duplicate_external_reference
    e.getFields();  // per-field messages for input problems
    e.getStatus();  // HTTP status, or 0 when the API wasn't reached (connection_failed)
}
```

Branch on `getCode()`; the message is for people and may change.

## Lists

Lists return a `Page`: iterate it, and read `getPaging()` for `page`, `pages` and `total`.

```java
Page page = afropin.brand().payments().list(Map.of("limit", 50));
for (Object payment : page) { ... }
if (page.hasMore()) {
    Page next = afropin.brand().payments().list(Map.of("limit", 50, "page", ((Number) page.getPaging().get("page")).intValue() + 1));
}
```

## Idempotency and retries

Each request gets a new `Idempotency-Key`. To use your own, pass it after the parameters: `sell(params, key)`. Nothing
is retried for you. If a sale times out, send it again with the same `external_reference`: it either goes through once,
or fails with `duplicate_external_reference`.

## Webhooks

```java
Map<String, Object> event = Webhooks.parse(rawBody, request.getHeader("webhook-secret"), webhookSecret);
if ("pin.spent".equals(event.get("type"))) { ... }
```

`parse` throws `invalid_signature` when the signature doesn't match. Use the raw body exactly as received.
