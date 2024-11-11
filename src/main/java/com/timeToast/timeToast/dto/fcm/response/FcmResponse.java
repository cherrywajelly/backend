package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;

public record FcmResponse(

        FcmConstant fcmConstant,

        String nickname,

        String toastName
){
}
