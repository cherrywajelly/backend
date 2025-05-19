package com.timeToast.timeToast.dto.toast_piece.response;

import java.util.List;

public record ToastPieceResponses(
        long giftToastId,
        List<ToastPieceResponse> toastPieceResponses
) {
}
