package com.timeToast.timeToast.dto.toast_piece.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ToastPieceResponse(
        long memberId,
        String nickname,
        String profileUrl,
        long iconId,
        String title,
        String contentsUrl,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.mm.dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        List<ToastPieceImage> toastPieceImages


) {
    public static ToastPieceResponse from(final ToastPieceMember toastPieceMember,
                                          final ToastPiece toastPiece, final List<ToastPieceImage> toastPieceImages){
        return ToastPieceResponse.builder()
                .memberId(toastPieceMember.memberId())
                .nickname(toastPieceMember.nickname())
                .profileUrl(toastPieceMember.profileUrl())
                .iconId(toastPiece.getIconId())
                .title(toastPiece.getTitle())
                .contentsUrl(toastPiece.getContentsUrl())
                .createdAt(toastPiece.getCreatedAt())
                .toastPieceImages(toastPieceImages)
                .build();
    }
}
