package com.timeToast.timeToast.dto.fcm.requset;


import lombok.Builder;

@Builder
public record FcmRequest(

    boolean validateOnly,

    FcmMessageRequest message

) {
    public static FcmRequest toRequest(FcmMessageRequest fcmMessage, final boolean validateOnly) {
        return FcmRequest.builder()
                .message(fcmMessage)
                .validateOnly(validateOnly)
                .build();
    }

}
