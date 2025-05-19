package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.giftToast.gift_toast.GiftToast;
import lombok.Builder;

@Builder
public record GiftToastIncompleteResponse(
        long giftToastId,
        String title,
        String iconImageUrl
) {
    public static GiftToastIncompleteResponse from(final GiftToast giftToast, final String iconImageUrl){

        return GiftToastIncompleteResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .build();
    }
}
