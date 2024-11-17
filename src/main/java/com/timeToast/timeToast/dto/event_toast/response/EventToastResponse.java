package com.timeToast.timeToast.dto.event_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EventToastResponse(
        long eventToastId,

        String title,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate openedDate,

        boolean isOpened,

        String iconImageUrl,

        long memberId,

        String memberProfileUrl,

        String nickname,

        Integer jamCount,

        long dDay,

        boolean isWritten,

        List<JamResponses> jams
){
    public static EventToastResponse fromEntity(EventToast eventToast, final String iconImageUrl, final long memberId, final String memberProfileUrl, final String nickname, final Integer jamCount, final long dDay, final List<JamResponses> jams) {
        return EventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .isOpened(eventToast.isOpened())
                .iconImageUrl(iconImageUrl)
                .memberId(memberId)
                .memberProfileUrl(memberProfileUrl)
                .nickname(nickname)
                .jamCount(jamCount)
                .dDay(dDay)
                .jams(jams)
                .build();
    }

    public static EventToastResponse of(EventToastResponse eventToastResponse, boolean isWritten) {
        return EventToastResponse.builder()
                .eventToastId(eventToastResponse.eventToastId())
                .title(eventToastResponse.title())
                .openedDate(eventToastResponse.openedDate())
                .isOpened(eventToastResponse.isOpened())
                .iconImageUrl(eventToastResponse.iconImageUrl())
                .memberProfileUrl(eventToastResponse.memberProfileUrl())
                .nickname(eventToastResponse.nickname())
                .jamCount(eventToastResponse.jamCount())
                .dDay(eventToastResponse.dDay())
                .isWritten(isWritten)
                .jams(eventToastResponse.jams())
                .build();
    }
}
