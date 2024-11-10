package com.timeToast.timeToast.controller.search;

import com.timeToast.timeToast.dto.search.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.service.search.SearchService;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("")
    public SearchResponses searchNickname(@RequestBody final SearchRequest searchRequest){
      return searchService.searchNickname(searchRequest);
    }
}
