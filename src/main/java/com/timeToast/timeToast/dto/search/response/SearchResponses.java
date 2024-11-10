package com.timeToast.timeToast.dto.search.response;

import java.util.List;
public record SearchResponses(
        int nextPage,
        int size,
        List<SearchResponse> searchResponses
) {
}
