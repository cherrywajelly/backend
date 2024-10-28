package com.timeToast.timeToast.repository.toast_piece.toast_piece_image;

import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import java.util.List;

public interface ToastPieceImageRepository {

    ToastPieceImage save(final ToastPieceImage toastPieceImage);
    List<ToastPieceImage> findAllByToastPieceId(final long toastPieceId);
    void deleteToastPieceImage(final ToastPieceImage toastPieceImage);
}
