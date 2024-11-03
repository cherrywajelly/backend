package com.timeToast.timeToast.dto.gift_toast.request;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.time.LocalDate;
import java.util.List;

public record GiftToastGroupRequest(
        Long iconId,
        Long groupId,
        LocalDate memorizedDate,
        LocalDate openedDate,
        String title,
        List<Long> giftToastMembers
) {

    public static GiftToast to(GiftToastGroupRequest giftToastRequest){
        return GiftToast.builder()
                .iconId(giftToastRequest.iconId())
                .groupId(giftToastRequest.groupId())
                .title(giftToastRequest.title())
                .memorizedDate(giftToastRequest.memorizedDate())
                .openedDate(giftToastRequest.openedDate())
                .isOpened(false)
                .giftToastType(GiftToastType.GROUP)
                .build();
    }
}
