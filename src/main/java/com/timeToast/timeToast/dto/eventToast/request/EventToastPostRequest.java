package com.timeToast.timeToast.dto.eventToast.request;

import com.timeToast.timeToast.domain.eventToast.EventToast;

import java.time.LocalDate;

public record EventToastPostRequest(
        LocalDate openedDate,

        String title,

        Long iconId,

        String description
){
    public EventToast toEntity(EventToastPostRequest eventToastPostRequest, final long memberId){
        return EventToast.builder()
                .openedDate(eventToastPostRequest.openedDate)
                .title(eventToastPostRequest.title)
                .iconId(eventToastPostRequest.iconId)
                .memberId(memberId)
                .description(eventToastPostRequest.description)
                .build();
    }
}
