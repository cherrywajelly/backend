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
        Long groupId,
        String groupName,
        GiftToastType giftToastType,
        LocalDate memorizedDate,
        LocalDate openedDate,
        Boolean isAgree,
        Boolean isOpened,
        List<GiftToastOwnerResponse> giftToastOwnerResponses

) {
    public static GiftToastResponse from(final GiftToast giftToast,final String iconImageUrl,
                                         final List<GiftToastOwnerResponse> giftToastOwnerResponses,final String groupName){
        return GiftToastResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .groupId(giftToast.getGroupId())
                .iconImageUrl(iconImageUrl)
                .groupName(groupName)
                .giftToastType(giftToast.getGiftToastType())
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .isAgree(giftToast.getIsAgree())
                .isOpened(giftToast.getIsOpened())
                .giftToastOwnerResponses(giftToastOwnerResponses)
                .build();
    }

}
