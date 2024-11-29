package com.timeToast.timeToast.dto.fcm.requset;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import lombok.Builder;

@Builder
public record FcmPostRequest(

        FcmConstant fcmConstant,

        String nickname,

        String toastName,

        // eventToastId | giftToastId | followerId
        long param
){
}
