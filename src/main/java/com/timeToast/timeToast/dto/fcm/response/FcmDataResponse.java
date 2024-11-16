package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.fcm.Fcm;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FcmDataResponse(
    Long memberId,

    FcmConstant fcmConstant,

    String nickname,

    String toastName,

    long param,

    String imageUrl

) {
    public static FcmDataResponse fromFcmResponse(FcmResponse fcmResponse, final long memberId) {
        return FcmDataResponse.builder()
                .memberId(memberId)
                .fcmConstant(fcmResponse.fcmConstant())
                .nickname(fcmResponse.nickname())
                .toastName(fcmResponse.toastName())
                .param(fcmResponse.param())
                .build();
    }

    public Fcm toEntity(FcmDataResponse fcmDataResponse, String imageUrl) {
        return Fcm.builder()
                .memberId(fcmDataResponse.memberId())
                .fcmConstant(fcmDataResponse.fcmConstant())
                .nickname(fcmDataResponse.nickname())
                .toastName(fcmDataResponse.toastName())
                .param(fcmDataResponse.param())
                .imageUrl(imageUrl)
                .build();
    }

}