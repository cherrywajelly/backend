package com.timeToast.timeToast.dto.gift_toast.request;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.time.LocalDate;
import java.util.List;

public record GiftToastFriendRequest(
        Long iconId,
        Long friendId,
        LocalDate memorizedDate,
        LocalDate openedDate,
        String title
) {

    public static GiftToast to(GiftToastFriendRequest giftToastFriendRequest){
        return GiftToast.builder()
                .iconId(giftToastFriendRequest.iconId())
                .memorizedDate(giftToastFriendRequest.memorizedDate())
                .openedDate(giftToastFriendRequest.openedDate())
                .isOpened(false)
                .title(giftToastFriendRequest.title())
                .giftToastType(GiftToastType.FRIEND)
                .build();
    }
}
