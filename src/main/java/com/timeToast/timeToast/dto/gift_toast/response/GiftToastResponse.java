package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GiftToastResponse(
        long giftToastId,
        String title,
        String iconImageUrl,
        GiftToastType giftToastType,
        String giftToastOwner,
        Boolean isOpened

) {
    public static GiftToastResponse from(final GiftToast giftToast,final String iconImageUrl,
                                         final String giftToastOwner){
        return GiftToastResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .giftToastType(giftToast.getGiftToastType())
                .giftToastOwner(giftToastOwner)
                .isOpened(giftToast.getIsOpened())
                .build();
    }

}
