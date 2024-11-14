package com.timeToast.timeToast.dto.event_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
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

        String memberProfileUrl,

        String nickname,

        Integer jamCount,

        long dDay,

        List<JamResponse> jams
){
    public static EventToastResponse fromEntity(EventToast eventToast, final String iconImageUrl, final String memberProfileUrl, final String nickname, final Integer jamCount, final long dDay, final List<JamResponse> jams) {
        return EventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .isOpened(eventToast.isOpened())
                .iconImageUrl(iconImageUrl)
                .memberProfileUrl(memberProfileUrl)
                .nickname(nickname)
                .jamCount(jamCount)
                .dDay(dDay)
                .jams(jams)
                .build();
    }
}
