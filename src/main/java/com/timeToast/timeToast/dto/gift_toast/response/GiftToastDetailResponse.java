package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GiftToastDetailResponse(
        long giftToastId,
        String title,
        String iconImageUrl,
        GiftToastType giftToastType,
        String giftToastOwner,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate memorizedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate openedDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdDate,
        Boolean isOpened,
        Long dDay,
        ToastPieceResponses toastPieceResponses
) {
    public static GiftToastDetailResponse from( final GiftToast giftToast, final String iconImageUrl, final Long dDay,
                                         final String giftToastOwner, final ToastPieceResponses toastPieceResponses){
        return GiftToastDetailResponse.builder()
                .giftToastId(giftToast.getId())
                .title(giftToast.getTitle())
                .iconImageUrl(iconImageUrl)
                .giftToastType(giftToast.getGiftToastType())
                .giftToastOwner(giftToastOwner)
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .createdDate(giftToast.getCreatedAt().toLocalDate())
                .isOpened(giftToast.getIsOpened())
                .dDay(dDay)
                .toastPieceResponses(toastPieceResponses)
                .build();
    }
}
