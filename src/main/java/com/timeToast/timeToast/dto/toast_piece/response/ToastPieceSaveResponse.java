package com.timeToast.timeToast.dto.toast_piece.response;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import lombok.Builder;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ToastPieceSaveResponse(
        long toastPieceId,
        long giftToastId,
        long iconId,
        String title,
        String contentsUrl,
        List<String> toastPieceImages
) {

    public static ToastPieceSaveResponse from(final ToastPiece toastPiece){

        List<String> toastPieceImageUrls = toastPiece.getToastPieceImages()
                .stream()
                .map(ToastPieceImage::getImageUrl)
                .collect(Collectors.toList());

        return ToastPieceSaveResponse.builder()
                .toastPieceId(toastPiece.getId())
                .giftToastId(toastPiece.getGiftToastId())
                .iconId(toastPiece.getIconId())
                .title(toastPiece.getTitle())
                .contentsUrl(toastPiece.getContentsUrl())
                .toastPieceImages(toastPieceImageUrls)
                .build();
    }
}
