package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.fcm.Fcm;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FcmDataResponse(
    Long memberId,

    FcmConstant fcmConstant,

    String nickname,

    String toastName,

    LocalDate time

) {
    public static FcmDataResponse fromFcmResponse(FcmResponse fcmDataResponse, final long memberId,  LocalDate time) {
        return FcmDataResponse.builder()
                .memberId(memberId)
                .fcmConstant(fcmDataResponse.fcmConstant())
                .nickname(fcmDataResponse.nickname())
                .toastName(fcmDataResponse.toastName())
                .time(time)
                .build();
    }

    public Fcm toEntity(FcmDataResponse fcmDataRequest) {
        return Fcm.builder()
                .memberId(fcmDataRequest.memberId())
                .fcmConstant(fcmDataRequest.fcmConstant())
                .nickname(fcmDataRequest.nickname())
                .toastName(fcmDataRequest.toastName())
                .time(fcmDataRequest.time())
                .build();
    }

}