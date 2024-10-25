package com.timeToast.timeToast.dto.event_toast.request;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;

import java.time.LocalDate;

public record EventToastPostRequest(
        LocalDate opened_date,

        String title,
        Long icon_id
){
    public EventToast toEntity(EventToastPostRequest eventToastPostRequest, final Long memberId){
        return EventToast.builder()
                .openedDate(eventToastPostRequest.opened_date)
                .title(eventToastPostRequest.title)
                .iconId(eventToastPostRequest.icon_id)
                .memberId(memberId)
                .build();
    }
}
