package com.timeToast.timeToast.dto.fcm.response;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.fcm.Fcm;
import lombok.Builder;

@Builder
public record FcmResponses(
        long id,

        FcmConstant fcmConstant,

        String nickname,

        String text,

//        String imageUrl,

        String time,

        String toastName,

        boolean isOpened
) {
    public static FcmResponses fromEntity(Fcm fcm, String text, String time){
        return FcmResponses.builder()
                .id(fcm.getId())
                .fcmConstant(fcm.getFcmConstant())
                .nickname(fcm.getNickname())
                .text(text)
                .time(time)
                .toastName(fcm.getToastName())
                .build();
    }
}
