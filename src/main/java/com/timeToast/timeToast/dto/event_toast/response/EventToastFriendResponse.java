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

        IconResponse icon
){
    public static EventToastFriendResponse fromEntity(EventToast eventToast, IconResponse icon, boolean postedJam) {
        return EventToastFriendResponse.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpenedDate())
                .icon(icon)
                .postedJam(postedJam)
                .build();
    }
}