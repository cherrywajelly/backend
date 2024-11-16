package com.timeToast.timeToast.dto.toast_piece.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ToastPieceResponse(
        long memberId,
        long toastPieceId,
        String nickname,
        String profileUrl,
        String iconImageUrl,
        String title,
        String contentsUrl,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,
        List<String> toastPieceImages


) {
    public static ToastPieceResponse from(final ToastPieceMember toastPieceMember, final ToastPiece toastPiece,
                                          final String iconImageUrl, final List<String> toastPieceImages){
        return ToastPieceResponse.builder()
                .memberId(toastPieceMember.memberId())
                .toastPieceId(toastPiece.getId())
                .nickname(toastPieceMember.nickname())
                .profileUrl(toastPieceMember.profileUrl())
                .iconImageUrl(iconImageUrl)
                .title(toastPiece.getTitle())
                .contentsUrl(toastPiece.getContentsUrl())
                .createdAt(toastPiece.getCreatedAt().toLocalDate())
                .toastPieceImages(toastPieceImages)
                .build();
    }
}
