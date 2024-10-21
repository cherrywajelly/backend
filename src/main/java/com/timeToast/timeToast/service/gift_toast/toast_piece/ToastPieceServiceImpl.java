package com.timeToast.timeToast.service.gift_toast.toast_piece;

import com.timeToast.timeToast.repository.gift_toast.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.gift_toast.toast_piece_image.ToastPieceImageRepository;

public class ToastPieceServiceImpl implements ToastPieceService{

    private final ToastPieceRepository toastPieceRepository;
    private final ToastPieceImageRepository toastPieceImageRepository;

    public ToastPieceServiceImpl(final ToastPieceRepository toastPieceRepository, final ToastPieceImageRepository toastPieceImageRepository) {
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
    }


}
