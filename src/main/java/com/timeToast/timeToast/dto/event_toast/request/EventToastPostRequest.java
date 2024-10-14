package com.timeToast.timeToast.dto.event_toast.request;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.Member;

import java.time.LocalDate;

public record EventToastPostRequest(
        LocalDate opened_date,

        String title
){
    public EventToast toEntity(EventToastPostRequest eventToastPostRequest, Member member){
        return EventToast.builder()
                .opened_date(eventToastPostRequest.opened_date)
                .title(eventToastPostRequest.title)
                .member(member)
                .build();
    }
}
