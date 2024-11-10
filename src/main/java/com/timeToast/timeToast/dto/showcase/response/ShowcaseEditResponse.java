package com.timeToast.timeToast.dto.showcase.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ShowcaseEditResponse(
        long eventToastId,
        String iconUrl,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
