package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.util.List;

@Builder
public record GiftToastIncompleteResponse(
        long giftToastId,
        String title,
        String iconImageUrl,
        List<GiftToastIncompleteMember> giftToastMembers
) {
    public static GiftToastIncompleteResponse from(final GiftToast giftToast, final String iconImageUrl,
                                                   final List<GiftToastIncompleteMember> giftToastMembers){
        return GiftToastIncompleteResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .giftToastMembers(giftToastMembers)
                .build();
    }
}
