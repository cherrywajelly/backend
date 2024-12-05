package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GiftToastSaveResponse(
        long giftToastId,
        String title,
        GiftToastType giftToastType,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate memorizedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate openedDate,
        Boolean isOpened
) {

    public static GiftToastSaveResponse from(final GiftToast giftToast){
        return GiftToastSaveResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .giftToastType(giftToast.getGiftToastType())
                .description(giftToast.getDescription())
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .isOpened(giftToast.getIsOpened())
                .build();
    }
}
