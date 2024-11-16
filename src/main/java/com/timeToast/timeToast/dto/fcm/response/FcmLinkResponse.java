package com.timeToast.timeToast.dto.fcm.response;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import lombok.Builder;

@Builder
public record FcmLinkResponse(

        String fcmConstant,

        String param
){
}
