package com.timeToast.timeToast.dto.search;

public record SearchRequest(
        int page,
        int size,
        String searchKeyword
) {
}
