package com.timeToast.timeToast.repository.toast_piece.toast_piece;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToastPieceJpaRepository extends JpaRepository<ToastPiece, Long> {
    List<ToastPiece> findAllByGiftToastId(final long giftToastId);
    List<ToastPiece> findAllByMemberIdAndGiftToastId(final long memberId, final long giftToastId);
}
