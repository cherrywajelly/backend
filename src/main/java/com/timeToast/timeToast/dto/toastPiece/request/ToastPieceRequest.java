package com.timeToast.timeToast.dto.toastPiece.request;

import com.timeToast.timeToast.domain.toastPiece.toast_piece.ToastPiece;

public record ToastPieceRequest(
        long giftToastId,
        long iconId,
        String title

) {

    public static ToastPiece to(final long memberId, final ToastPieceRequest toastPieceRequest){
        return ToastPiece.builder()
                .memberId(memberId)
                .giftToastId(toastPieceRequest.giftToastId())
                .iconId(toastPieceRequest.iconId())
                .title(toastPieceRequest.title())
                .build();
    }
}
