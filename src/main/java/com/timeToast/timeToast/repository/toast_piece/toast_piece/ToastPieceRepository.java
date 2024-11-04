package com.timeToast.timeToast.repository.toast_piece.toast_piece;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;

import java.util.List;
import java.util.Optional;

public interface ToastPieceRepository {

    ToastPiece saveToastPiece(final ToastPiece toastPiece);
    Optional<ToastPiece> findById(final long toastPieceId);
    List<ToastPiece> findAllByGiftToastId(final long giftToastId);
    List<ToastPiece> findAllByMemberIdAndGiftToastId(final long memberId, final long giftToastId);
    void deleteToastPiece(final ToastPiece toastPiece);

}
