package com.timeToast.timeToast.dto.toast_piece.response;

import com.timeToast.timeToast.dto.gift_toast.response.GiftToastInfo;
import lombok.Builder;

@Builder
public record ToastPieceDetailResponse(
        GiftToastInfo giftToastInfo,
        ToastPieceResponse toastPieceResponse
) {
}
