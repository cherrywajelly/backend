package com.timeToast.timeToast.dto.gift_toast.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.time.LocalDate;

public record GiftToastGroupRequest(
        Long iconId,
        Long teamId,

        LocalDate memorizedDate,
        LocalDate openedDate,
        String title
) {

    public static GiftToast to(GiftToastGroupRequest giftToastRequest){
        return GiftToast.builder()
                .iconId(giftToastRequest.iconId())
                .teamId(giftToastRequest.teamId())
                .title(giftToastRequest.title())
                .memorizedDate(giftToastRequest.memorizedDate())
                .openedDate(giftToastRequest.openedDate())
                .isOpened(false)
                .giftToastType(GiftToastType.GROUP)
                .build();
    }
}
