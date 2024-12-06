package com.timeToast.timeToast.dto.fcm.response;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.fcm.Fcm;
import lombok.Builder;

@Builder
public record FcmResponse(
        long fcmId,

        FcmConstant fcmConstant,

        String nickname,

        String text,

        String imageUrl,

        String time,

        String toastName,

        boolean isOpened,

        long param
) {
    public static FcmResponse fromEntity(Fcm fcm, String text, String time, String nickname, long param){
        return FcmResponse.builder()
                .fcmId(fcm.getId())
                .fcmConstant(fcm.getFcmConstant())
                .nickname(nickname)
                .text(text)
                .imageUrl(fcm.getImageUrl())
                .time(time)
                .toastName(fcm.getToastName())
                .isOpened(fcm.isOpened())
                .param(param)
                .build();
    }
}
