package com.timeToast.timeToast.dto.event_toast.response.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ManagerEventToastResponse(
        long eventToastId,
        String iconImageUrl,
        String title,
        String nickname,
        LocalDate openedDate,
        boolean isOpened,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt
) {
    public static ManagerEventToastResponse from(final EventToast eventToast, final String iconImageUrl, final String nickname) {
        return ManagerEventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .nickname(nickname)
                .openedDate(eventToast.getOpenedDate())
                .isOpened(eventToast.isOpened())
                .createdAt(eventToast.getCreatedAt().toLocalDate())
                .build();
    }
}
