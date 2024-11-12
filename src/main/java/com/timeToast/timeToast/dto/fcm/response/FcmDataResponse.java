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

    String toastName

) {
    public static FcmDataResponse fromFcmResponse(FcmResponse fcmDataResponse, final long memberId) {
        return FcmDataResponse.builder()
                .memberId(memberId)
                .fcmConstant(fcmDataResponse.fcmConstant())
                .nickname(fcmDataResponse.nickname())
                .toastName(fcmDataResponse.toastName())
                .build();
    }

    public Fcm toEntity(FcmDataResponse fcmDataResponse) {
        return Fcm.builder()
                .memberId(fcmDataResponse.memberId())
                .fcmConstant(fcmDataResponse.fcmConstant())
                .nickname(fcmDataResponse.nickname())
                .toastName(fcmDataResponse.toastName())
                .build();
    }

}