package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastResponses(
        Long event_toast_id,
        String title,
        LocalDate opened_date,

        String nickname,

        IconResponse icon
){
    public static EventToastResponses fromEntity(EventToast eventToast, final String nickname, IconResponse icon){
        return EventToastResponses.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpenedDate())
                .nickname(nickname)
                .icon(icon)
                .build();
    }
}

