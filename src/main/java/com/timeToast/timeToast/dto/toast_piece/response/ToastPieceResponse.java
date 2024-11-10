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
        String iconImageUrl,
        String title,
        String contentsUrl,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.mm.dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        List<String> toastPieceImages


) {
    public static ToastPieceResponse from(final ToastPieceMember toastPieceMember, final ToastPiece toastPiece,
                                          final String iconImageUrl, final List<String> toastPieceImages){
        return ToastPieceResponse.builder()
                .memberId(toastPieceMember.memberId())
                .nickname(toastPieceMember.nickname())
                .profileUrl(toastPieceMember.profileUrl())
                .iconImageUrl(iconImageUrl)
                .title(toastPiece.getTitle())
                .contentsUrl(toastPiece.getContentsUrl())
                .createdAt(toastPiece.getCreatedAt())
                .toastPieceImages(toastPieceImages)
                .build();
    }
}
