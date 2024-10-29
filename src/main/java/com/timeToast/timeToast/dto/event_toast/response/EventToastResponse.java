package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastResponse(
        Long event_toast_id,
        String title,

        String nickname,
        LocalDate opened_date
        // 잼 목록 반환
){
    public static EventToastResponse fromEntity(EventToast eventToast, final String nickname){
        return EventToastResponse.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpenedDate())
                .nickname(nickname)
                // 잼 목록 반환
                .build();
    }
}
