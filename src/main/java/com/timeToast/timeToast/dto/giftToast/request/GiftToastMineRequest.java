package com.timeToast.timeToast.dto.giftToast.request;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.giftToast.gift_toast.GiftToast;

import java.time.LocalDate;

public record GiftToastMineRequest(
        Long iconId,
        LocalDate memorizedDate,
        LocalDate openedDate,
        String title,
        String description
) {
    public static GiftToast to(GiftToastMineRequest giftToastMineRequest){
        return GiftToast.builder()
                .iconId(giftToastMineRequest.iconId())
                .memorizedDate(giftToastMineRequest.memorizedDate())
                .openedDate(giftToastMineRequest.openedDate())
                .isOpened(false)
                .title(giftToastMineRequest.title())
                .giftToastType(GiftToastType.MINE)
                .description(giftToastMineRequest.description())
                .build();
    }
}
