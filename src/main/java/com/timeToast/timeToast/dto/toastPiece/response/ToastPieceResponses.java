package com.timeToast.timeToast.dto.toastPiece.response;

import java.util.List;

public record ToastPieceResponses(
        long giftToastId,
        List<ToastPieceResponse> toastPieceResponses
) {
}
