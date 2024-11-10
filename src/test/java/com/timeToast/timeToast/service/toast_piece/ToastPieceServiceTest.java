package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToastPieceServiceTest implements ToastPieceService {

    @Override
    public ToastPieceSaveResponse saveToastPiece(long memberId, ToastPieceRequest toastPieceRequest) {
        return new ToastPieceSaveResponse(1, 1, 1, "title","contentsUrl", List.of("images"));
    }

    @Override
    public ToastPieceSaveResponse saveToastPieceContents(long memberId, long toastPieceId, MultipartFile contents) {
        return new ToastPieceSaveResponse(1, 1, 1, "title","contentsUrl", List.of("images"));
    }

    @Override
    public ToastPieceSaveResponse saveToastPieceImages(long memberId, long toastPieceId, List<MultipartFile> toastPieceImages) {
        return new ToastPieceSaveResponse(1, 1, 1, "title","contentsUrl", List.of("images"));
    }

    @Override
    public ToastPieceResponses getToastPiecesByGiftToastId(long giftToastId) {
        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        toastPieceResponses.add(
                new ToastPieceResponse(1, "nickname","profileUrl", "iconImageUrl", "title", "contentsUrl", LocalDateTime.now(), List.of("images"))
        );
        return new ToastPieceResponses(1, toastPieceResponses);
    }

    @Override
    public ToastPieceResponse getToastPiece(long toastPieceId) {
        return new ToastPieceResponse(1, "nickname","profileUrl", "iconImageUrl", "title", "contentsUrl", LocalDateTime.now(), List.of("images"));
    }

    @Override
    public void deleteToastPieceByMemberIdAndToastPieceId(long memberId, long toastPieceId) {

    }
}