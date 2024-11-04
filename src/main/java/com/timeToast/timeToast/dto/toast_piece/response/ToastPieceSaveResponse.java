package com.timeToast.timeToast.dto.toast_piece.response;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import lombok.Builder;
import java.util.List;

@Builder
public record ToastPieceSaveResponse(
        long toastPieceId,
        long giftToastId,
        long iconId,
        String title,
        String contentsUrl,
        List<String> toastPieceImages
) {

    public static ToastPieceSaveResponse from(final ToastPiece toastPiece, final List<String> toastPieceImages){
        return ToastPieceSaveResponse.builder()
                .toastPieceId(toastPiece.getId())
                .giftToastId(toastPiece.getGiftToastId())
                .iconId(toastPiece.getIconId())
                .title(toastPiece.getTitle())
                .contentsUrl(toastPiece.getContentsUrl())
                .toastPieceImages(toastPieceImages)
                .build();
    }
}
