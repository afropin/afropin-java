// Generated from sdks/spec/endpoints.json by sdks/spec/generate.py. Don't edit by hand: change the spec and run it again.

package com.afropin;

import java.util.Map;

/** The reseller API (/api/v1/reseller): every call a reseller account can make, by area. */
public final class Reseller {
    private final Wallets wallets;
    private final Stores stores;
    private final Cards cards;
    private final Catalog catalog;
    private final Pins pins;
    private final BrandStores brandStores;
    private final Cashouts cashouts;

    Reseller(AfroPin client) {
        this.wallets = new Wallets(client);
        this.stores = new Stores(client);
        this.cards = new Cards(client);
        this.catalog = new Catalog(client);
        this.pins = new Pins(client);
        this.brandStores = new BrandStores(client);
        this.cashouts = new Cashouts(client);
    }

    public Wallets wallets() {
        return wallets;
    }

    public Stores stores() {
        return stores;
    }

    public Cards cards() {
        return cards;
    }

    public Catalog catalog() {
        return catalog;
    }

    public Pins pins() {
        return pins;
    }

    public BrandStores brandStores() {
        return brandStores;
    }

    public Cashouts cashouts() {
        return cashouts;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> map(Object value) {
        return (Map<String, Object>) value;
    }

    /** Reseller wallets */
    public static final class Wallets {
        private final AfroPin client;

        Wallets(AfroPin client) {
            this.client = client;
        }

        /** Your wallets and their balances, one per currency. (GET /api/v1/reseller/wallets) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/wallets"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }
    }

    /** Reseller stores */
    public static final class Stores {
        private final AfroPin client;

        Stores(AfroPin client) {
            this.client = client;
        }

        /** Your stores. (GET /api/v1/reseller/stores) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/stores"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** Add a store. (POST /api/v1/reseller/stores) */
        public Map<String, Object> create(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/stores"), params, idempotencyKey, false));
        }

        public Map<String, Object> create(Map<String, ?> params) {
            return create(params, null);
        }

        public Map<String, Object> create() {
            return create(Map.of(), null);
        }

        /** Edit a store. (POST /api/v1/reseller/stores/{store_id}) */
        public Map<String, Object> update(String storeId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/stores/{store_id}", storeId), params, idempotencyKey, false));
        }

        public Map<String, Object> update(String storeId, Map<String, ?> params) {
            return update(storeId, params, null);
        }

        public Map<String, Object> update(String storeId) {
            return update(storeId, Map.of(), null);
        }

        /** Remove a store. (DELETE /api/v1/reseller/stores/{store_id}) */
        public Map<String, Object> delete(String storeId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("DELETE", AfroPin.path("reseller/stores/{store_id}", storeId), params, idempotencyKey, false));
        }

        public Map<String, Object> delete(String storeId, Map<String, ?> params) {
            return delete(storeId, params, null);
        }

        public Map<String, Object> delete(String storeId) {
            return delete(storeId, Map.of(), null);
        }
    }

    /** Reseller cards */
    public static final class Cards {
        private final AfroPin client;

        Cards(AfroPin client) {
            this.client = client;
        }

        /** Look up one of your printed cards before activating it. (POST /api/v1/reseller/cards/lookup) */
        public Map<String, Object> lookup(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cards/lookup"), params, idempotencyKey, false));
        }

        public Map<String, Object> lookup(Map<String, ?> params) {
            return lookup(params, null);
        }

        public Map<String, Object> lookup() {
            return lookup(Map.of(), null);
        }

        /** Activate a printed card: sells a PIN for the amount loaded. (POST /api/v1/reseller/cards/activate) */
        public Map<String, Object> activate(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cards/activate"), params, idempotencyKey, false));
        }

        public Map<String, Object> activate(Map<String, ?> params) {
            return activate(params, null);
        }

        public Map<String, Object> activate() {
            return activate(Map.of(), null);
        }
    }

    /** Reseller catalog */
    public static final class Catalog {
        private final AfroPin client;

        Catalog(AfroPin client) {
            this.client = client;
        }

        /** The products you can sell. (GET /api/v1/reseller/catalog) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/catalog"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** One product you can sell. (GET /api/v1/reseller/catalog/{catalog_id}) */
        public Map<String, Object> get(String catalogId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("reseller/catalog/{catalog_id}", catalogId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String catalogId, Map<String, ?> params) {
            return get(catalogId, params, null);
        }

        public Map<String, Object> get(String catalogId) {
            return get(catalogId, Map.of(), null);
        }
    }

    /** Reseller pins */
    public static final class Pins {
        private final AfroPin client;

        Pins(AfroPin client) {
            this.client = client;
        }

        /** Sell a PIN. The response has its full code (pin_code), returned only here. (POST /api/v1/reseller/pins) */
        public Map<String, Object> sell(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins"), params, idempotencyKey, false));
        }

        public Map<String, Object> sell(Map<String, ?> params) {
            return sell(params, null);
        }

        public Map<String, Object> sell() {
            return sell(Map.of(), null);
        }

        /** One of your PINs: balance, status, masks, customer, family cards. (GET /api/v1/reseller/pins/{pin_id}) */
        public Map<String, Object> get(String pinId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("reseller/pins/{pin_id}", pinId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String pinId, Map<String, ?> params) {
            return get(pinId, params, null);
        }

        public Map<String, Object> get(String pinId) {
            return get(pinId, Map.of(), null);
        }

        /** A PIN's movements, newest first. (GET /api/v1/reseller/pins/{pin_id}/movements) */
        public Page movements(String pinId, Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/pins/{pin_id}/movements", pinId), params, idempotencyKey, true);
        }

        public Page movements(String pinId, Map<String, ?> params) {
            return movements(pinId, params, null);
        }

        public Page movements(String pinId) {
            return movements(pinId, Map.of(), null);
        }

        /** Add value to a PIN. (POST /api/v1/reseller/pins/top-up) */
        public Map<String, Object> topUp(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/top-up"), params, idempotencyKey, false));
        }

        public Map<String, Object> topUp(Map<String, ?> params) {
            return topUp(params, null);
        }

        public Map<String, Object> topUp() {
            return topUp(Map.of(), null);
        }

        /** Block (freeze) a PIN. Its cardholder can't undo it. (POST /api/v1/reseller/pins/block) */
        public Map<String, Object> block(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/block"), params, idempotencyKey, false));
        }

        public Map<String, Object> block(Map<String, ?> params) {
            return block(params, null);
        }

        public Map<String, Object> block() {
            return block(Map.of(), null);
        }

        /** Unblock a PIN. (POST /api/v1/reseller/pins/unblock) */
        public Map<String, Object> unblock(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/unblock"), params, idempotencyKey, false));
        }

        public Map<String, Object> unblock(Map<String, ?> params) {
            return unblock(params, null);
        }

        public Map<String, Object> unblock() {
            return unblock(Map.of(), null);
        }

        /** Give a PIN a new code; the old one stops working. (POST /api/v1/reseller/pins/new-code) */
        public Map<String, Object> newCode(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/new-code"), params, idempotencyKey, false));
        }

        public Map<String, Object> newCode(Map<String, ?> params) {
            return newCode(params, null);
        }

        public Map<String, Object> newCode() {
            return newCode(Map.of(), null);
        }

        /** Links that add a PIN to Google Wallet or Apple Wallet. (POST /api/v1/reseller/pins/wallet-links) */
        public Map<String, Object> walletLinks(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/wallet-links"), params, idempotencyKey, false));
        }

        public Map<String, Object> walletLinks(Map<String, ?> params) {
            return walletLinks(params, null);
        }

        public Map<String, Object> walletLinks() {
            return walletLinks(Map.of(), null);
        }

        /** Change a PIN's webhook. (POST /api/v1/reseller/pins/webhook) */
        public Map<String, Object> setWebhook(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/pins/webhook"), params, idempotencyKey, false));
        }

        public Map<String, Object> setWebhook(Map<String, ?> params) {
            return setWebhook(params, null);
        }

        public Map<String, Object> setWebhook() {
            return setWebhook(Map.of(), null);
        }

        /** A PIN's cashouts. (GET /api/v1/reseller/pins/{pin_id}/cashouts) */
        public Page cashouts(String pinId, Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/pins/{pin_id}/cashouts", pinId), params, idempotencyKey, true);
        }

        public Page cashouts(String pinId, Map<String, ?> params) {
            return cashouts(pinId, params, null);
        }

        public Page cashouts(String pinId) {
            return cashouts(pinId, Map.of(), null);
        }
    }

    /** Reseller brandStores */
    public static final class BrandStores {
        private final AfroPin client;

        BrandStores(AfroPin client) {
            this.client = client;
        }

        /** Brand stores where your customers can spend the PINs you sell. (GET /api/v1/reseller/brand-stores) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("reseller/brand-stores"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }
    }

    /** Reseller cashouts */
    public static final class Cashouts {
        private final AfroPin client;

        Cashouts(AfroPin client) {
            this.client = client;
        }

        /** Request a cashout (only while cashouts are switched on). (POST /api/v1/reseller/cashouts) */
        public Map<String, Object> request(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cashouts"), params, idempotencyKey, false));
        }

        public Map<String, Object> request(Map<String, ?> params) {
            return request(params, null);
        }

        public Map<String, Object> request() {
            return request(Map.of(), null);
        }

        /** Send the customer the confirmation code again. (POST /api/v1/reseller/cashouts/send-code) */
        public Map<String, Object> sendCode(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cashouts/send-code"), params, idempotencyKey, false));
        }

        public Map<String, Object> sendCode(Map<String, ?> params) {
            return sendCode(params, null);
        }

        public Map<String, Object> sendCode() {
            return sendCode(Map.of(), null);
        }

        /** Confirm a cashout with the customer's code. (POST /api/v1/reseller/cashouts/{cashout_id}/confirm) */
        public Map<String, Object> confirm(String cashoutId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cashouts/{cashout_id}/confirm", cashoutId), params, idempotencyKey, false));
        }

        public Map<String, Object> confirm(String cashoutId, Map<String, ?> params) {
            return confirm(cashoutId, params, null);
        }

        public Map<String, Object> confirm(String cashoutId) {
            return confirm(cashoutId, Map.of(), null);
        }

        /** Void an unpaid cashout. (POST /api/v1/reseller/cashouts/{cashout_id}/void) */
        public Map<String, Object> voidCashout(String cashoutId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("reseller/cashouts/{cashout_id}/void", cashoutId), params, idempotencyKey, false));
        }

        public Map<String, Object> voidCashout(String cashoutId, Map<String, ?> params) {
            return voidCashout(cashoutId, params, null);
        }

        public Map<String, Object> voidCashout(String cashoutId) {
            return voidCashout(cashoutId, Map.of(), null);
        }
    }
}
