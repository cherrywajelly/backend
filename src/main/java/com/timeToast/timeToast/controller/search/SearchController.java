package com.timeToast.timeToast.controller.search;

import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.service.search.SearchService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("")
    public SearchResponses searchNickname(@RequestBody final SearchRequest searchRequest){
      return searchService.searchNickname(searchRequest);
    }
}
