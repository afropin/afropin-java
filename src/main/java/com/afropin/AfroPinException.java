package com.afropin;

import java.util.Map;

/**
 * A request the API refused, or one that couldn't be made. Branch on {@link #getCode()} (for example pin_balance_low or
 * duplicate_external_reference); the message is for people and may change. getFields() has per-field messages for
 * input problems. getStatus() is the HTTP status, or 0 when the API wasn't reached.
 */
public class AfroPinException extends RuntimeException {
    private final String code;
    private final Map<String, Object> fields;
    private final int status;

    public AfroPinException(String code, String message, Map<String, Object> fields, int status) {
        super(message);
        this.code = code;
        this.fields = fields;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public int getStatus() {
        return status;
    }
}
