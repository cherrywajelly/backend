package com.timeToast.timeToast.dto.search.request;

public record SearchRequest(
        int page,
        int size,
        String searchKeyword
) {
}
