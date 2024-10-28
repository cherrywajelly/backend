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

        IconResponse icon

        //TODO 잼 바름 여부 추가
){
    public static EventToastFriendResponse fromEntity(EventToast eventToast, IconResponse icon){
        return EventToastFriendResponse.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpenedDate())
                .icon(icon)
                //TODO 잼 바름 여부 추가
                .build();
    }
}