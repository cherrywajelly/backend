package com.timeToast.timeToast.dto.event_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastFriendResponse(
        long eventToastId,

        String title,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate openedDate,

        boolean postedJam,

        String nickname,

        String memberProfileUrl,

        IconResponse icon
){
    public static EventToastFriendResponse fromEntity(EventToast eventToast, IconResponse icon, final String nickname, boolean postedJam) {
        return EventToastFriendResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .nickname(nickname)
                .icon(icon)
                .postedJam(postedJam)
                .build();
    }
}