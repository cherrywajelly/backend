package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShowcaseServiceTest implements ShowcaseService{

    @Override
    public ShowcaseSaveResponses saveShowcase(long memberId, ShowcaseSaveRequest showcaseSaveRequest) {
        return new ShowcaseSaveResponses(List.of("title"));
    }

    @Override
    public ShowcaseEditResponses getShowcaseSaveList(long memberId) {
        List<ShowcaseEditResponse> showcaseEditResponses = new ArrayList<>();
        showcaseEditResponses.add(
                new ShowcaseEditResponse(1L, "iconUrl", "title", LocalDate.of(2024, 1, 1), false, 0L )
        );
        return new ShowcaseEditResponses(showcaseEditResponses);
    }

    @Override
    public ShowcaseResponses getShowcase(long memberId) {
        List<ShowcaseResponse> showcaseResponses = new ArrayList<>();
        showcaseResponses.add(
                new ShowcaseResponse(1L, "iconUrl")
        );
        return new ShowcaseResponses(showcaseResponses);
    }

    @Override
    public void deleteShowcase(long memberId, long showcaseId) {

    }
}