package com.timeToast.timeToast.dto.fcm.requset;


import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;

public record FcmMessageRequest (

        FcmLinkResponse data,

        FcmNotificationRequest notification,

        String token
){
}

