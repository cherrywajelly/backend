package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponse;
import com.timeToast.timeToast.dto.search.response.SearchResponses;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest implements SearchService{

    @Override
    public SearchResponses searchNickname(SearchRequest searchRequest) {
        List<SearchResponse> searchResponses = new ArrayList<>();
        searchResponses.add(new SearchResponse(1, "nickname", "profileUrl"));
        return new SearchResponses(1, 1, searchResponses);
    }
}