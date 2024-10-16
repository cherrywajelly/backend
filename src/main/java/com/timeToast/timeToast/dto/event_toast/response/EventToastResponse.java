package com.timeToast.timeToast.dto.event_toast.response;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastResponse (
        long event_toast_id,
        String title,
        LocalDate opened_date,

        String nickname,

        IconResponse icon
){
    public static EventToastResponse fromEntity(EventToast eventToast, Member member, IconResponse icon){
        return EventToastResponse.builder()
                .event_toast_id(eventToast.getId())
                .title(eventToast.getTitle())
                .opened_date(eventToast.getOpened_date())
                .nickname(member.getNickname())
                .icon(icon)
                .build();
    }
}

