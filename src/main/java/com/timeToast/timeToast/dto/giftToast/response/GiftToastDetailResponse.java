package com.timeToast.timeToast.dto.giftToast.response;

import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceResponses;
import lombok.Builder;

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
