package com.timeToast.timeToast.dto.gift_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GiftToastDetailResponse(

        GiftToastInfo giftToastInfo,
        Long dDay,
        ToastPieceResponses toastPieceResponses
) {
    public static GiftToastDetailResponse from(final GiftToastInfo giftToastInfo, final Long dDay,
                                               final ToastPieceResponses toastPieceResponses){
        return GiftToastDetailResponse.builder()
                .giftToastInfo(giftToastInfo)
                .dDay(dDay)
                .toastPieceResponses(toastPieceResponses)
                .build();
    }
}
