package com.afropin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

/**
 * Checking a webhook AfroPin sent you. Each one is signed with the webhook's secret: the webhook-secret header holds the
 * HMAC-SHA256 of the raw body. Check it against the body exactly as received, before parsing it.
 */
public final class Webhooks {
    private Webhooks() {
    }

    public static boolean verify(String rawBody, String signature, String secret) {
        if (signature == null || signature.isEmpty()) {
            return false;
        }
        byte[] expected = AfroPin.hmacHex(secret, rawBody).getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(expected, signature.getBytes(StandardCharsets.UTF_8));
    }

    /** The event, a Map with "type" and "data", once its signature checks out. */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String rawBody, String signature, String secret) {
        if (!verify(rawBody, signature, secret)) {
            throw new AfroPinException("invalid_signature", "The webhook signature does not match its body", null, 0);
        }
        Object event = Json.read(rawBody);
        if (!(event instanceof Map) || !((Map<String, Object>) event).containsKey("type")) {
            throw new AfroPinException("invalid_response", "The webhook body is not an AfroPin event", null, 0);
        }
        return (Map<String, Object>) event;
    }
}
