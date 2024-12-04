package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GiftToastInfo(
        long giftToastId,
        String title,
        String iconImageUrl,
        GiftToastType giftToastType,
        String giftToastOwner,
        String profileImageUrl,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate memorizedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate openedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdDate,
        Boolean isOpened

) {

    public static GiftToastInfo from(final GiftToast giftToast, final String iconImageUrl,
                                     final String profileImageUrl, final String giftToastOwner){

        return GiftToastInfo.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .giftToastType(giftToast.getGiftToastType())
                .giftToastOwner(giftToastOwner)
                .profileImageUrl(profileImageUrl)
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .createdDate(giftToast.getCreatedAt().toLocalDate())
                .isOpened(giftToast.getIsOpened())
                .build();
    }
}
