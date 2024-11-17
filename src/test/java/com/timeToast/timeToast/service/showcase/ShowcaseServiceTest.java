package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.response.Response;


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
                new ShowcaseEditResponse(1L, 0L,false, "iconUrl", "title", LocalDate.of(2024, 1, 1) )
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
    public Response deleteShowcase(long memberId, long showcaseId) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }
}