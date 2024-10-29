package com.timeToast.timeToast.dto.jam.request;

import com.timeToast.timeToast.domain.jam.Jam;

import java.time.LocalDate;

public record JamRequest(

        String title,

        long iconId
){
    public Jam toEntity(JamRequest jamRequest, final long memberId, final long eventToastId){
        return Jam.builder()
                .title(jamRequest.title)
                .iconId(jamRequest.iconId)
                .memberId(memberId)
                .eventToastId(eventToastId)
                .build();
    }
}
