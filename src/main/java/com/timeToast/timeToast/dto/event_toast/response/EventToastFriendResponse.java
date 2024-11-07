package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastFriendResponse(
        Long event_toast_id,

        String title,

        LocalDate opened_date,

        boolean postedJam,

        String nickname,

        IconResponse icon
){
    public static EventToastFriendResponse fromEntity(EventToast eventToast, IconResponse icon, final String nickname, boolean postedJam) {
        return EventToastFriendResponse.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpenedDate())
                .nickname(nickname)
                .icon(icon)
                .postedJam(postedJam)
                .build();
    }
}