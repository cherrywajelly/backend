package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GiftToastManagerResponse (
        long giftToastId,
        String iconImageUrl,
        String title,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate memorizedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate openedDate,
        boolean isOpened,
        GiftToastType giftToastType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt
) {
    public static GiftToastManagerResponse from(final GiftToast giftToast, final String iconImageUrl, final String name) {
        return GiftToastManagerResponse.builder()
                .giftToastId(giftToast.getId())
                .iconImageUrl(iconImageUrl)
                .title(giftToast.getTitle())
                .name(name)
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .isOpened(true)
                .giftToastType(giftToast.getGiftToastType())
                .createdAt(giftToast.getCreatedAt().toLocalDate())
                .build();
    }
}