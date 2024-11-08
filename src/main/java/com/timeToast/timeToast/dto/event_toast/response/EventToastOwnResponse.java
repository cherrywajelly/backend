package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastOwnResponse(
        Long eventToastId,

        String title,

        LocalDate openedDate,

        IconResponse icon
){
    public static EventToastOwnResponse fromEntity(EventToast eventToast, IconResponse icon){
        return EventToastOwnResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .icon(icon)
                .build();
    }
}
