package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseEditResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;

public interface ShowcaseService {

    ShowcaseSaveResponses saveShowcase(final long memberId, final ShowcaseSaveRequest showcaseSaveRequest);
    ShowcaseEditResponses getShowcaseSaveList(final long memberId);
    ShowcaseResponses getShowcase(final long memberId);
    void deleteShowcase(final long memberId, final long showcaseId);
}