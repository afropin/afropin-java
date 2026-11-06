package com.afropin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * One page of a list. Iterate it for the records; getPaging() is null when you asked for page=all. Ask for the next page
 * with Map.of("page", page + 1).
 */
public final class Page implements Iterable<Object> {
    private final List<Object> data;
    private final Map<String, Object> paging;

    Page(List<Object> data, Map<String, Object> paging) {
        this.data = data;
        this.paging = paging;
    }

    public List<Object> getData() {
        return data;
    }

    public Map<String, Object> getPaging() {
        return paging;
    }

    public boolean hasMore() {
        return paging != null && ((Number) paging.get("page")).intValue() < ((Number) paging.get("pages")).intValue();
    }

    @Override
    public Iterator<Object> iterator() {
        return data.iterator();
    }
}
