package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ToastPieceService {

    ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest);
    ToastPieceSaveResponse saveToastPieceContents(final long memberId, final long toastPieceId, final MultipartFile contents);
    ToastPieceSaveResponse saveToastPieceImages(final long memberId, final long toastPieceId, final List<MultipartFile> toastPieceImages);
    ToastPieceResponses getToastPiecesByGiftToastId(final long giftToastId);
    ToastPieceResponse getToastPiece(final long toastPieceId);
    void deleteToastPieceByMemberIdAndToastPieceId(final long memberId, final long toastPieceId);
}
