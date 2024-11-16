package com.timeToast.timeToast.dto.fcm.requset;

import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import lombok.*;

@Builder
public record FcmSendRequest(
        String token,

        FcmNotificationRequest notification,

        FcmLinkResponse data
) {
    public FcmSendRequest toSend(FcmSendRequest fcmSendDto) {
        return FcmSendRequest.builder()
                .token(fcmSendDto.token)
                .notification(fcmSendDto.notification)
                .data(fcmSendDto.data)
                .build();
    }
}