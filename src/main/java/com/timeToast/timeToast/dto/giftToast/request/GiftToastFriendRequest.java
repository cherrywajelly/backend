package com.timeToast.timeToast.dto.giftToast.request;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.giftToast.gift_toast.GiftToast;

import java.time.LocalDate;

public record GiftToastFriendRequest(
        Long iconId,
        Long friendId,
        LocalDate memorizedDate,
        LocalDate openedDate,
        String title,
        String description
) {

    public static GiftToast to(GiftToastFriendRequest giftToastFriendRequest){
        return GiftToast.builder()
                .iconId(giftToastFriendRequest.iconId())
                .memorizedDate(giftToastFriendRequest.memorizedDate())
                .openedDate(giftToastFriendRequest.openedDate())
                .isOpened(false)
                .title(giftToastFriendRequest.title())
                .giftToastType(GiftToastType.FRIEND)
                .description(giftToastFriendRequest.description())
                .build();
    }
}
