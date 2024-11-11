package com.timeToast.timeToast.dto.fcm.requset;

import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;


public record FcmMessageRequest (

        FcmDataRequest data,

        FcmNotificationRequest notification,

        String token
){
}

