// Generated from sdks/spec/endpoints.json by sdks/spec/generate.py. Don't edit by hand: change the spec and run it again.

package com.afropin;

import java.util.Map;

/** The brand API (/api/v1/brand): every call a brand account can make, by area. */
public final class Brand {
    private final Wallets wallets;
    private final Stores stores;
    private final Cards cards;
    private final IssuingCatalog issuingCatalog;
    private final BrandStores brandStores;
    private final Pins pins;
    private final Catalog catalog;
    private final ResellerStores resellerStores;
    private final Payments payments;
    private final Quotes quotes;

    Brand(AfroPin client) {
        this.wallets = new Wallets(client);
        this.stores = new Stores(client);
        this.cards = new Cards(client);
        this.issuingCatalog = new IssuingCatalog(client);
        this.brandStores = new BrandStores(client);
        this.pins = new Pins(client);
        this.catalog = new Catalog(client);
        this.resellerStores = new ResellerStores(client);
        this.payments = new Payments(client);
        this.quotes = new Quotes(client);
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

    public IssuingCatalog issuingCatalog() {
        return issuingCatalog;
    }

    public BrandStores brandStores() {
        return brandStores;
    }

    public Pins pins() {
        return pins;
    }

    public Catalog catalog() {
        return catalog;
    }

    public ResellerStores resellerStores() {
        return resellerStores;
    }

    public Payments payments() {
        return payments;
    }

    public Quotes quotes() {
        return quotes;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> map(Object value) {
        return (Map<String, Object>) value;
    }

    /** Brand wallets */
    public static final class Wallets {
        private final AfroPin client;

        Wallets(AfroPin client) {
            this.client = client;
        }

        /** Your wallets and their balances, one per currency. (GET /api/v1/brand/wallets) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/wallets"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }
    }

    /** Brand stores */
    public static final class Stores {
        private final AfroPin client;

        Stores(AfroPin client) {
            this.client = client;
        }

        /** Your stores. (GET /api/v1/brand/stores) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/stores"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** Add a store. (POST /api/v1/brand/stores) */
        public Map<String, Object> create(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/stores"), params, idempotencyKey, false));
        }

        public Map<String, Object> create(Map<String, ?> params) {
            return create(params, null);
        }

        public Map<String, Object> create() {
            return create(Map.of(), null);
        }

        /** Edit a store. (POST /api/v1/brand/stores/{store_id}) */
        public Map<String, Object> update(String storeId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/stores/{store_id}", storeId), params, idempotencyKey, false));
        }

        public Map<String, Object> update(String storeId, Map<String, ?> params) {
            return update(storeId, params, null);
        }

        public Map<String, Object> update(String storeId) {
            return update(storeId, Map.of(), null);
        }

        /** Remove a store. (DELETE /api/v1/brand/stores/{store_id}) */
        public Map<String, Object> delete(String storeId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("DELETE", AfroPin.path("brand/stores/{store_id}", storeId), params, idempotencyKey, false));
        }

        public Map<String, Object> delete(String storeId, Map<String, ?> params) {
            return delete(storeId, params, null);
        }

        public Map<String, Object> delete(String storeId) {
            return delete(storeId, Map.of(), null);
        }
    }

    /** Brand cards */
    public static final class Cards {
        private final AfroPin client;

        Cards(AfroPin client) {
            this.client = client;
        }

        /** Look up one of your printed cards before activating it. (POST /api/v1/brand/cards/lookup) */
        public Map<String, Object> lookup(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/cards/lookup"), params, idempotencyKey, false));
        }

        public Map<String, Object> lookup(Map<String, ?> params) {
            return lookup(params, null);
        }

        public Map<String, Object> lookup() {
            return lookup(Map.of(), null);
        }

        /** Activate a printed card: sells a PIN for the amount loaded. (POST /api/v1/brand/cards/activate) */
        public Map<String, Object> activate(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/cards/activate"), params, idempotencyKey, false));
        }

        public Map<String, Object> activate(Map<String, ?> params) {
            return activate(params, null);
        }

        public Map<String, Object> activate() {
            return activate(Map.of(), null);
        }
    }

    /** Brand issuingCatalog */
    public static final class IssuingCatalog {
        private final AfroPin client;

        IssuingCatalog(AfroPin client) {
            this.client = client;
        }

        /** The products you can sell. (GET /api/v1/brand/issuing-catalog) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/issuing-catalog"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** One product you can sell. (GET /api/v1/brand/issuing-catalog/{catalog_id}) */
        public Map<String, Object> get(String catalogId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("brand/issuing-catalog/{catalog_id}", catalogId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String catalogId, Map<String, ?> params) {
            return get(catalogId, params, null);
        }

        public Map<String, Object> get(String catalogId) {
            return get(catalogId, Map.of(), null);
        }
    }

    /** Brand brandStores */
    public static final class BrandStores {
        private final AfroPin client;

        BrandStores(AfroPin client) {
            this.client = client;
        }

        /** Brand stores where a PIN of one of your products can be spent: pass catalog_id. (GET /api/v1/brand/brand-stores) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/brand-stores"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }
    }

    /** Brand pins */
    public static final class Pins {
        private final AfroPin client;

        Pins(AfroPin client) {
            this.client = client;
        }

        /** Sell a PIN. The response has its full code (pin_code), returned only here. (POST /api/v1/brand/pins) */
        public Map<String, Object> sell(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins"), params, idempotencyKey, false));
        }

        public Map<String, Object> sell(Map<String, ?> params) {
            return sell(params, null);
        }

        public Map<String, Object> sell() {
            return sell(Map.of(), null);
        }

        /** One of your PINs: balance, status, masks, customer, family cards. (GET /api/v1/brand/pins/{pin_id}) */
        public Map<String, Object> get(String pinId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("brand/pins/{pin_id}", pinId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String pinId, Map<String, ?> params) {
            return get(pinId, params, null);
        }

        public Map<String, Object> get(String pinId) {
            return get(pinId, Map.of(), null);
        }

        /** A PIN's movements, newest first. (GET /api/v1/brand/pins/{pin_id}/movements) */
        public Page movements(String pinId, Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/pins/{pin_id}/movements", pinId), params, idempotencyKey, true);
        }

        public Page movements(String pinId, Map<String, ?> params) {
            return movements(pinId, params, null);
        }

        public Page movements(String pinId) {
            return movements(pinId, Map.of(), null);
        }

        /** Add value to a PIN. (POST /api/v1/brand/pins/top-up) */
        public Map<String, Object> topUp(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/top-up"), params, idempotencyKey, false));
        }

        public Map<String, Object> topUp(Map<String, ?> params) {
            return topUp(params, null);
        }

        public Map<String, Object> topUp() {
            return topUp(Map.of(), null);
        }

        /** Block (freeze) a PIN. Its cardholder can't undo it. (POST /api/v1/brand/pins/block) */
        public Map<String, Object> block(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/block"), params, idempotencyKey, false));
        }

        public Map<String, Object> block(Map<String, ?> params) {
            return block(params, null);
        }

        public Map<String, Object> block() {
            return block(Map.of(), null);
        }

        /** Unblock a PIN. (POST /api/v1/brand/pins/unblock) */
        public Map<String, Object> unblock(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/unblock"), params, idempotencyKey, false));
        }

        public Map<String, Object> unblock(Map<String, ?> params) {
            return unblock(params, null);
        }

        public Map<String, Object> unblock() {
            return unblock(Map.of(), null);
        }

        /** Give a PIN a new code; the old one stops working. (POST /api/v1/brand/pins/new-code) */
        public Map<String, Object> newCode(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/new-code"), params, idempotencyKey, false));
        }

        public Map<String, Object> newCode(Map<String, ?> params) {
            return newCode(params, null);
        }

        public Map<String, Object> newCode() {
            return newCode(Map.of(), null);
        }

        /** Links that add a PIN to Google Wallet or Apple Wallet. (POST /api/v1/brand/pins/wallet-links) */
        public Map<String, Object> walletLinks(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/wallet-links"), params, idempotencyKey, false));
        }

        public Map<String, Object> walletLinks(Map<String, ?> params) {
            return walletLinks(params, null);
        }

        public Map<String, Object> walletLinks() {
            return walletLinks(Map.of(), null);
        }

        /** Change a PIN's webhook. (POST /api/v1/brand/pins/webhook) */
        public Map<String, Object> setWebhook(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/webhook"), params, idempotencyKey, false));
        }

        public Map<String, Object> setWebhook(Map<String, ?> params) {
            return setWebhook(params, null);
        }

        public Map<String, Object> setWebhook() {
            return setWebhook(Map.of(), null);
        }

        /** Choose who delivers a PIN's codes to its customer: us (handle_delivery true) or you. (POST /api/v1/brand/pins/delivery) */
        public Map<String, Object> setDelivery(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/delivery"), params, idempotencyKey, false));
        }

        public Map<String, Object> setDelivery(Map<String, ?> params) {
            return setDelivery(params, null);
        }

        public Map<String, Object> setDelivery() {
            return setDelivery(Map.of(), null);
        }

        /** Check a PIN before redeeming it; nothing changes. (POST /api/v1/brand/pins/check) */
        public Map<String, Object> check(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/check"), params, idempotencyKey, false));
        }

        public Map<String, Object> check(Map<String, ?> params) {
            return check(params, null);
        }

        public Map<String, Object> check() {
            return check(Map.of(), null);
        }

        /** Redeem a PIN: take an amount off it and credit your wallet. (POST /api/v1/brand/pins/redeem) */
        public Map<String, Object> redeem(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/pins/redeem"), params, idempotencyKey, false));
        }

        public Map<String, Object> redeem(Map<String, ?> params) {
            return redeem(params, null);
        }

        public Map<String, Object> redeem() {
            return redeem(Map.of(), null);
        }
    }

    /** Brand catalog */
    public static final class Catalog {
        private final AfroPin client;

        Catalog(AfroPin client) {
            this.client = client;
        }

        /** The products you accept. (GET /api/v1/brand/catalog) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/catalog"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** One product you accept. (GET /api/v1/brand/catalog/{catalog_id}) */
        public Map<String, Object> get(String catalogId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("brand/catalog/{catalog_id}", catalogId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String catalogId, Map<String, ?> params) {
            return get(catalogId, params, null);
        }

        public Map<String, Object> get(String catalogId) {
            return get(catalogId, Map.of(), null);
        }
    }

    /** Brand resellerStores */
    public static final class ResellerStores {
        private final AfroPin client;

        ResellerStores(AfroPin client) {
            this.client = client;
        }

        /** Reseller stores that sell PINs you accept. (GET /api/v1/brand/reseller-stores) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/reseller-stores"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }
    }

    /** Brand payments */
    public static final class Payments {
        private final AfroPin client;

        Payments(AfroPin client) {
            this.client = client;
        }

        /** The redemptions credited to you, newest first. (GET /api/v1/brand/payments) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/payments"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** One redemption. (GET /api/v1/brand/payments/{transaction_ref}) */
        public Map<String, Object> get(String transactionRef, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("brand/payments/{transaction_ref}", transactionRef), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String transactionRef, Map<String, ?> params) {
            return get(transactionRef, params, null);
        }

        public Map<String, Object> get(String transactionRef) {
            return get(transactionRef, Map.of(), null);
        }
    }

    /** Brand quotes */
    public static final class Quotes {
        private final AfroPin client;

        Quotes(AfroPin client) {
            this.client = client;
        }

        /** Your quotes, newest first. (GET /api/v1/brand/quotes) */
        public Page list(Map<String, ?> params, String idempotencyKey) {
            return (Page) client.request("GET", AfroPin.path("brand/quotes"), params, idempotencyKey, true);
        }

        public Page list(Map<String, ?> params) {
            return list(params, null);
        }

        public Page list() {
            return list(Map.of(), null);
        }

        /** Quote a business for your gift cards in bulk; returns a pay_url. (POST /api/v1/brand/quotes) */
        public Map<String, Object> create(Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/quotes"), params, idempotencyKey, false));
        }

        public Map<String, Object> create(Map<String, ?> params) {
            return create(params, null);
        }

        public Map<String, Object> create() {
            return create(Map.of(), null);
        }

        /** One quote, by id or QTE- reference. (GET /api/v1/brand/quotes/{quote_id}) */
        public Map<String, Object> get(String quoteId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("GET", AfroPin.path("brand/quotes/{quote_id}", quoteId), params, idempotencyKey, false));
        }

        public Map<String, Object> get(String quoteId, Map<String, ?> params) {
            return get(quoteId, params, null);
        }

        public Map<String, Object> get(String quoteId) {
            return get(quoteId, Map.of(), null);
        }

        /** Withdraw an unpaid quote. (POST /api/v1/brand/quotes/{quote_id}/cancel) */
        public Map<String, Object> cancel(String quoteId, Map<String, ?> params, String idempotencyKey) {
            return map(client.request("POST", AfroPin.path("brand/quotes/{quote_id}/cancel", quoteId), params, idempotencyKey, false));
        }

        public Map<String, Object> cancel(String quoteId, Map<String, ?> params) {
            return cancel(quoteId, params, null);
        }

        public Map<String, Object> cancel(String quoteId) {
            return cancel(quoteId, Map.of(), null);
        }
    }
}
