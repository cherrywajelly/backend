package com.timeToast.timeToast.dto.gift_toast.response;

import lombok.Builder;

@Builder
public record GiftToastManagerResponse (
        long giftToastId,
        String iconImageUrl,
        String title,
        String name
) {
    public static GiftToastManagerResponse from(long giftToastId, String iconImageUrl, String title, String name) {
        return GiftToastManagerResponse.builder()
                .giftToastId(giftToastId)
                .iconImageUrl(iconImageUrl)
                .title(title)
                .name(name)
                .build();
    }
}