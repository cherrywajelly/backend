package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastInfo;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_TOAST_PIECE;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.TOAST_PIECE_NOT_EXISTS;

public class ToastPieceServiceTest implements ToastPieceService {

    @Override
    public ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest,
                                                 final MultipartFile contents,final List<MultipartFile> toastPieceImages) {
        return new ToastPieceSaveResponse(1, 1, 1, "title","contentsUrl", List.of("images"));
    }


    @Override
    public ToastPieceResponses getToastPiecesByGiftToastId(long giftToastId) {

        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        toastPieceResponses.add(
                ToastPieceResponse.builder()
                        .memberId(1L)
                        .toastPieceId(1L)
                        .nickname("nickname")
                        .profileUrl("profileUrl")
                        .iconImageUrl("iconImageUrl")
                        .title("title")
                        .contentsUrl("contentsUrl")
                        .createdAt(LocalDate.now())
                        .toastPieceImages(List.of("images"))
                        .build());

        return new ToastPieceResponses(1, toastPieceResponses);
    }

    @Override
    public ToastPieceResponse getToastPieceResponse(long toastPieceId) {
        return null;
    }


    @Override
    public Response deleteToastPieceByMemberIdAndToastPieceId(long memberId, long toastPieceId) {
        if(toastPieceId==2){
            throw new NotFoundException(TOAST_PIECE_NOT_EXISTS.getMessage());
        }else if(toastPieceId==3){
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }
}