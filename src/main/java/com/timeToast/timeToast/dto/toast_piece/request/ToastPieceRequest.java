package com.timeToast.timeToast.dto.toast_piece.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;

import java.time.LocalDateTime;
import java.util.List;

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
