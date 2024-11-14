package com.timeToast.timeToast.dto.event_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventToastDataResponse(

        String eventToastTitle,
        String eventToastMemberProfile,
        String eventToastNickname,
        String eventToastIconImageUrl
){
    public static EventToastDataResponse fromEntity(EventToast eventToast, final String nickname, final String memberProfileUrl, final String imageurl ){
        return EventToastDataResponse.builder()
                .eventToastTitle(eventToast.getTitle())
                .eventToastMemberProfile(memberProfileUrl)
                .eventToastNickname(nickname)
                .eventToastIconImageUrl(imageurl)
                .build();
    }
}