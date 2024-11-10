package com.timeToast.timeToast.dto.showcase.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ShowcaseEditResponse(
        long eventToastId,
        long showcaseId,
        String iconUrl,
        String title,
        LocalDate openedDate,
        boolean isShowcase,
        Long showCaseId
) {

    public static ShowcaseEditResponse from(final EventToast eventToast, final String iconUrl, final boolean isShowcase, final Long showCaseId){
        return ShowcaseEditResponse.builder()
                .eventToastId(eventToast.getId())
                .iconUrl(iconUrl)
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .isShowcase(isShowcase)
                .showCaseId(showCaseId)
                .build();
    }
}
