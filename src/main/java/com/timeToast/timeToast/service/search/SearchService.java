package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;

public interface SearchService {
    SearchResponses searchNickname(final SearchRequest searchRequest);
}
