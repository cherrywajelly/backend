package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastResponse(
        Long eventToastId,

        String title,

        String nickname,

        LocalDate openedDate
){
    public static EventToastResponse fromEntity(EventToast eventToast, final String nickname){
        return EventToastResponse.builder()
                .eventToastId(eventToast.getId())
                .title(eventToast.getTitle())
                .openedDate(eventToast.getOpenedDate())
                .nickname(nickname)
                .build();
    }
}
