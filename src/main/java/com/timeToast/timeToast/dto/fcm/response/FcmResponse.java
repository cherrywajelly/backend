package com.timeToast.timeToast.dto.fcm.response;


import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import lombok.Builder;

@Builder
public record FcmResponse(

        FcmConstant fcmConstant,

        String nickname,

        String toastName,

        // eventToastId | giftToastId | followerId
        long param
){
}
