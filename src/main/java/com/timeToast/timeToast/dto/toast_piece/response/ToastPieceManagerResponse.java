package com.timeToast.timeToast.dto.toast_piece.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ToastPieceManagerResponse(
        long toastPieceId,
        String iconImageUrl,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,
        String nickname
) {
    public static ToastPieceManagerResponse from(final ToastPiece toastPiece, final String iconImageUrl, final String nickname) {
        return ToastPieceManagerResponse.builder()
                .toastPieceId(toastPiece.getId())
                .iconImageUrl(iconImageUrl)
                .title(toastPiece.getTitle())
                .createdAt(toastPiece.getCreatedAt().toLocalDate())
                .nickname(nickname)
                .build();
    }
}