package com.timeToast.timeToast.repository.toast_piece.toast_piece_image;

import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToastPieceImageJpaRepository extends JpaRepository<ToastPieceImage, Long> {

    List<ToastPieceImage> findAllByToastPieceId(final long ToastPieceId);
}
