package com.timeToast.timeToast.dto.gift_toast.request;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.time.LocalDate;
import java.util.List;

public record GiftToastRequest(
        Long iconId,
        Long groupId,
        LocalDate memorizedDate,
        LocalDate openedDate,
        Boolean isAgree,
        Boolean isOpened,
        String title,
        GiftToastType giftToastType,
        List<Long> giftToastMembers
) {

    public static GiftToast to(GiftToastRequest giftToastRequest){
        return GiftToast.builder()
                .iconId(giftToastRequest.iconId())
                .title(giftToastRequest.title())
                .groupId(giftToastRequest.groupId())
                .memorizedDate(giftToastRequest.memorizedDate())
                .openedDate(giftToastRequest.openedDate())
                .isAgree(giftToastRequest.isAgree())
                .isOpened(false)
                .giftToastType(giftToastRequest.giftToastType())
                .build();
    }
}
