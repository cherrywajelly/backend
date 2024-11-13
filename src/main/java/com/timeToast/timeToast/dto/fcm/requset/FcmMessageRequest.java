package com.timeToast.timeToast.dto.fcm.requset;


public record FcmMessageRequest (

        FcmDataRequest data,

        FcmNotificationRequest notification,

        String token
){
}

