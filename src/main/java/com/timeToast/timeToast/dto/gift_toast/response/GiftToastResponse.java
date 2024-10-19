package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GiftToastResponse(

        String title,
        Long iconId,
        Long groupId,
        String groupName,
        GiftToastType giftToastType,
        LocalDate memorizedDate,
        LocalDate openedDate,
        Boolean isAgree,
        Boolean isOpened,
        List<Long> giftToastMembers

) {
    public static GiftToastResponse from(final GiftToast giftToast, List<Long> giftToastMembers, String groupName){
        return GiftToastResponse.builder()
                .title(giftToast.getTitle())
                .groupId(giftToast.getGroupId())
                .iconId(giftToast.getIconId())
                .groupName(groupName)
                .giftToastType(giftToast.getGiftToastType())
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .isAgree(giftToast.getIsAgree())
                .isOpened(giftToast.getIsOpened())
                .giftToastMembers(giftToastMembers)
                .build();
    }

}
