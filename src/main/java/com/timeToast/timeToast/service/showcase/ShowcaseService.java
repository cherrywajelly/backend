package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;
import org.springframework.web.bind.annotation.PathVariable;

public interface ShowcaseService {

    ShowcaseSaveResponse saveShowcase(final long memberId, final ShowcaseSaveRequest showcaseSaveRequest);
    ShowcaseSaveResponses getShowcaseSaveList(final long memberId);
    ShowcaseResponses getShowcase(final long memberId);
    void deleteShowcase(final long showcaseId);
}