package com.timeToast.timeToast.dto.event_toast.response.member;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.Builder;

//TODO 삭제 필요
@Builder
public record EventToastJamResponse(

        String eventToastTitle,
        String eventToastMemberProfile,
        String eventToastNickname,
        String eventToastIconImageUrl
){
    public static EventToastJamResponse fromEntity(EventToast eventToast, final String nickname, final String memberProfileUrl, final String imageurl ){
        return EventToastJamResponse.builder()
                .eventToastTitle(eventToast.getTitle())
                .eventToastMemberProfile(memberProfileUrl)
                .eventToastNickname(nickname)
                .eventToastIconImageUrl(imageurl)
                .build();
    }
}