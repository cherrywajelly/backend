package com.timeToast.timeToast.dto.event_toast.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastResponse(
        long eventToastId,

        String title,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate openedDate,

        boolean isWritten,

        String nickname,

        String memberProfileUrl,

        IconResponse icon,

        long dDay
){
    public static EventToastResponse fromEntity(EventToast eventToast, final String nickname, final String memberProfileUrl, IconResponse icon, boolean isWritten, long dDay) {
        return EventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .nickname(nickname)
                .memberProfileUrl(memberProfileUrl)
                .icon(icon)
                .isWritten(isWritten)
                .dDay(dDay)
                .build();
    }

    public static EventToastResponse ofEntity(EventToast eventToast, IconResponse icon, final String nickname, String memberProfileUrl, boolean isWritten) {
        return EventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .nickname(nickname)
                .memberProfileUrl(memberProfileUrl)
                .icon(icon)
                .isWritten(isWritten)
                .build();
    }
}

