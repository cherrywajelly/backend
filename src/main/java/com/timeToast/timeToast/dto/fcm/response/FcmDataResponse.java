package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.global.constant.FcmConstant;

public record FcmDataResponse(

        Long memberId,

        FcmConstant fcmConstant,

        String nickname,

        String toastName
){
}
