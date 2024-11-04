package com.timeToast.timeToast.repository.toast_piece.toast_piece;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ToastPieceRepositoryImpl implements ToastPieceRepository{

    private final ToastPieceJpaRepository toastPieceJpaRepository;

    public ToastPieceRepositoryImpl(ToastPieceJpaRepository toastPieceJpaRepository) {
        this.toastPieceJpaRepository = toastPieceJpaRepository;
    }

    @Override
    public ToastPiece saveToastPiece(final ToastPiece toastPiece) {
        return toastPieceJpaRepository.save(toastPiece);
    }

    @Override
    public Optional<ToastPiece> findById(final long toastPieceId) {
        return toastPieceJpaRepository.findById(toastPieceId);
    }

    @Override
    public List<ToastPiece> findAllByGiftToastId(final long giftToastId) {
        return toastPieceJpaRepository.findAllByGiftToastId(giftToastId);
    }

    @Override
    public List<ToastPiece> findAllByMemberIdAndGiftToastId(final long memberId, final long giftToastId) {
        return toastPieceJpaRepository.findAllByMemberIdAndGiftToastId(memberId, giftToastId);
    }

    @Override
    public void deleteToastPiece(final ToastPiece toastPiece) {
        toastPieceJpaRepository.delete(toastPiece);
    }
}
