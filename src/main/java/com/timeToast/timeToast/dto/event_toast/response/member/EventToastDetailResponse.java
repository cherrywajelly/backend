package com.timeToast.timeToast.dto.event_toast.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EventToastDetailResponse(
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

        String description,

        List<JamResponse> jams
){
    public static EventToastDetailResponse fromEntity(final EventToast eventToast, final String iconImageUrl, final long memberId, final String memberProfileUrl, final String nickname, final Integer jamCount, final long dDay, final List<JamResponse> jams) {
        return EventToastDetailResponse.builder()
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
                .description(eventToast.getDescription())
                .jams(jams)
                .build();
    }

    public static EventToastDetailResponse of(EventToastDetailResponse eventToastDetailResponse, boolean isWritten) {
        return EventToastDetailResponse.builder()
                .eventToastId(eventToastDetailResponse.eventToastId())
                .title(eventToastDetailResponse.title())
                .openedDate(eventToastDetailResponse.openedDate())
                .isOpened(eventToastDetailResponse.isOpened())
                .iconImageUrl(eventToastDetailResponse.iconImageUrl())
                .memberId(eventToastDetailResponse.memberId())
                .memberProfileUrl(eventToastDetailResponse.memberProfileUrl())
                .nickname(eventToastDetailResponse.nickname())
                .jamCount(eventToastDetailResponse.jamCount())
                .dDay(eventToastDetailResponse.dDay())
                .description(eventToastDetailResponse.description())
                .isWritten(isWritten)
                .jams(eventToastDetailResponse.jams())
                .build();
    }
}
