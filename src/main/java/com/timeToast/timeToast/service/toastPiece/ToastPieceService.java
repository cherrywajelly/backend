package com.timeToast.timeToast.service.toastPiece;

import com.timeToast.timeToast.dto.toastPiece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceSaveResponse;

import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ToastPieceService {

    ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest,
                                          final MultipartFile contents,final List<MultipartFile> toastPieceImages);
    ToastPieceResponses getToastPiecesByGiftToastId(final long giftToastId);
    ToastPieceResponse getToastPieceResponse(final long toastPieceId);
    Response deleteToastPieceByMemberIdAndToastPieceId(final long memberId, final long toastPieceId);
}
