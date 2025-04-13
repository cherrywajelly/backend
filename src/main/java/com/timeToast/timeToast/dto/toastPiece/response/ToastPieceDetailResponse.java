package com.timeToast.timeToast.dto.toastPiece.response;

import com.timeToast.timeToast.dto.giftToast.response.GiftToastInfo;
import lombok.Builder;

@Builder
public record ToastPieceDetailResponse(
        GiftToastInfo giftToastInfo,
        ToastPieceResponse toastPieceResponse
) {
}
