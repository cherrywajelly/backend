package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceManagerResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GiftToastInfoManagerResponse(
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
        LocalDate createdAt,
        List<ToastPieceManagerResponse> toastPieceManagerResponses
) {
    public static GiftToastInfoManagerResponse from(final GiftToast giftToast, final String iconImageUrl, final String name, final List<ToastPieceManagerResponse> toastPieceManagerResponses) {
        return GiftToastInfoManagerResponse.builder()
                .giftToastId(giftToast.getId())
                .iconImageUrl(iconImageUrl)
                .title(giftToast.getTitle())
                .name(name)
                .memorizedDate(giftToast.getMemorizedDate())
                .openedDate(giftToast.getOpenedDate())
                .isOpened(giftToast.getIsOpened())
                .giftToastType(giftToast.getGiftToastType())
                .createdAt(giftToast.getCreatedAt().toLocalDate())
                .toastPieceManagerResponses(toastPieceManagerResponses)
                .build();
    }
}