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
        GiftToastTeamMember giftToastTeamMember,
        Long dDay,
        ToastPieceResponses toastPieceResponses
) {
    public static GiftToastDetailResponse from(final GiftToastInfo giftToastInfo, final GiftToastTeamMember giftToastTeamMember,
                                               final Long dDay, final ToastPieceResponses toastPieceResponses){
        return GiftToastDetailResponse.builder()
                .giftToastInfo(giftToastInfo)
                .giftToastTeamMember(giftToastTeamMember)
                .dDay(dDay)
                .toastPieceResponses(toastPieceResponses)
                .build();
    }
}
