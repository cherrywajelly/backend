package com.timeToast.timeToast.dto.fcm.requset;

import lombok.*;

@Builder
public record FcmSendRequest(
        String token,

        FcmNotificationRequest notification,

        FcmDataRequest data
) {
    public FcmSendRequest toSend(FcmSendRequest fcmSendDto) {
        return FcmSendRequest.builder()
                .token(fcmSendDto.token)
                .notification(fcmSendDto.notification)
                .data(fcmSendDto.data)
                .build();
    }
}