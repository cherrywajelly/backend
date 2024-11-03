package com.timeToast.timeToast.repository.toast_piece.toast_piece_image;

import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ToastPieceImageRepositoryImpl implements ToastPieceImageRepository {

    private final ToastPieceImageJpaRepository toastPieceImageJpaRepository;

    public ToastPieceImageRepositoryImpl(ToastPieceImageJpaRepository toastPieceImageJpaRepository) {
        this.toastPieceImageJpaRepository = toastPieceImageJpaRepository;
    }

    @Override
    public ToastPieceImage save(final ToastPieceImage toastPieceImage) {
        return toastPieceImageJpaRepository.save(toastPieceImage);
    }

    @Override
    public List<ToastPieceImage> findAllByToastPieceId(final long toastPieceId) {
        return toastPieceImageJpaRepository.findAllByToastPieceId(toastPieceId);
    }

    @Override
    public void deleteToastPieceImage(final ToastPieceImage toastPieceImage) {
        toastPieceImageJpaRepository.delete(toastPieceImage);
    }

    @Override
    public void deleteAllByToastPieceId(final long toastPieceId) {
        toastPieceImageJpaRepository.deleteAllByToastPieceId(toastPieceId);
    }

}
