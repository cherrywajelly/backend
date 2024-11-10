package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.dto.search.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import org.springframework.web.bind.annotation.RequestParam;

public interface SearchService {
    SearchResponses searchNickname(final SearchRequest searchRequest);
}
