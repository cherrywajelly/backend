package com.timeToast.timeToast.dto.gift_toast.response;

import lombok.Builder;

@Builder
public record GiftToastDataManagerResponse(
        String giftToastIconImage,
        String giftToastName
) {
    public static GiftToastDataManagerResponse from (final String giftToastIconImage, final String giftToastName) {
        return GiftToastDataManagerResponse.builder()
                .giftToastIconImage(giftToastIconImage)
                .giftToastName(giftToastName)
                .build();
    }
}