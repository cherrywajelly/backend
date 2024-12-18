package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import lombok.Builder;

@Builder
public record FcmDataResponse(
    Long memberId,

    FcmConstant fcmConstant,

    long senderId,

    String toastName,

    long param,

    String imageUrl

) {
    public static FcmDataResponse fromFcmResponse(FcmPostRequest fcmPostRequest, final long memberId) {
        return FcmDataResponse.builder()
                .memberId(memberId)
                .fcmConstant(fcmPostRequest.fcmConstant())
                .senderId(fcmPostRequest.senderId())
                .toastName(fcmPostRequest.toastName())
                .param(fcmPostRequest.param())
                .build();
    }

    public Fcm toEntity(FcmDataResponse fcmDataResponse, String imageUrl) {
        return Fcm.builder()
                .memberId(fcmDataResponse.memberId())
                .fcmConstant(fcmDataResponse.fcmConstant())
                .senderId(fcmDataResponse.senderId())
                .toastName(fcmDataResponse.toastName())
                .param(fcmDataResponse.param())
                .imageUrl(imageUrl)
                .build();
    }

}