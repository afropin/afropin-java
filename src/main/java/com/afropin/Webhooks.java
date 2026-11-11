package com.afropin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Checking a webhook AfroPin sent you. Each one carries three headers: webhook-id (the same on every retry of one
 * event), webhook-timestamp (Unix seconds, when it was sent) and webhook-signature, the HMAC-SHA256 of
 * "id.timestamp.body" with your webhook's secret. Check it against the body exactly as received, before parsing it.
 * A timestamp more than 5 minutes from now is refused, so a captured webhook can't be replayed later.
 *
 * <pre>
 * Map&lt;String, Object&gt; event = Webhooks.parse(rawBody, request.getHeader("webhook-id"),
 *     request.getHeader("webhook-timestamp"), request.getHeader("webhook-signature"), webhookSecret);
 * </pre>
 */
public final class Webhooks {
    /** How far webhook-timestamp may be from now, in seconds */
    public static final long TOLERANCE_SECONDS = 300;

    private Webhooks() {
    }

    public static boolean verify(String rawBody, String id, String timestamp, String signature, String secret) {
        try {
            check(rawBody, id, timestamp, signature, secret);
            return true;
        } catch (AfroPinException e) {
            return false;
        }
    }

    /**
     * The event, a Map with "id", "type" and "data", once its signature checks out. Use id to skip an event you've
     * already handled.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String rawBody, String id, String timestamp, String signature, String secret) {
        check(rawBody, id, timestamp, signature, secret);
        Object event = Json.read(rawBody);
        if (!(event instanceof Map) || !((Map<String, Object>) event).containsKey("type")) {
            throw new AfroPinException("invalid_response", "The webhook body is not an AfroPin event", null, 0);
        }
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("id", id.trim());
        out.putAll((Map<String, Object>) event);
        return out;
    }

    /** The same, from a map of headers (Spring's @RequestHeader Map, for example), matched without regard to case */
    public static Map<String, Object> parse(String rawBody, Map<String, String> headers, String secret) {
        return parse(rawBody, header(headers, "webhook-id"), header(headers, "webhook-timestamp"), header(headers, "webhook-signature"), secret);
    }

    private static void check(String rawBody, String id, String timestamp, String signature, String secret) {
        id = id == null ? "" : id.trim();
        timestamp = timestamp == null ? "" : timestamp.trim();
        signature = signature == null ? "" : signature.trim();
        if (id.isEmpty() || !timestamp.matches("\\d{1,18}") || signature.isEmpty()) {
            throw new AfroPinException("invalid_signature", "The webhook-id, webhook-timestamp or webhook-signature header is missing", null, 0);
        }
        if (Math.abs(System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) > TOLERANCE_SECONDS) {
            throw new AfroPinException("invalid_signature", "The webhook timestamp is too old or too far ahead: check your server clock", null, 0);
        }
        byte[] expected = AfroPin.hmacHex(secret, id + "." + timestamp + "." + rawBody).getBytes(StandardCharsets.UTF_8);
        if (!MessageDigest.isEqual(expected, signature.getBytes(StandardCharsets.UTF_8))) {
            throw new AfroPinException("invalid_signature", "The webhook signature does not match its body", null, 0);
        }
    }

    private static String header(Map<String, String> headers, String name) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
