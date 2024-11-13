package com.timeToast.timeToast.controller.toast_piece;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RequestMapping("/api/v1/toastPieces")
@RestController
public class ToastPieceController {

    private final ToastPieceService toastPieceService;
    private final GiftToastService giftToastService;
    public ToastPieceController(final ToastPieceService toastPieceService, final GiftToastService giftToastService) {
        this.giftToastService = giftToastService;
        this.toastPieceService = toastPieceService;
    }

    @PostMapping("")
    public ToastPieceSaveResponse saveToastPiece(@Login final LoginMember loginMember, @RequestPart final ToastPieceRequest toastPieceRequest,
                                                 @RequestPart("toastPieceContents") final MultipartFile toastPieceContents,@RequestPart("toastPieceImages") final List<MultipartFile> toastPieceImages){
        return toastPieceService.saveToastPiece(loginMember.id(), toastPieceRequest, toastPieceContents, toastPieceImages);
    }

    @GetMapping("/{toastPieceId}")
    public ToastPieceDetailResponse getToastPieceDetail(@Login final LoginMember loginMember, @PathVariable final long toastPieceId){
        return giftToastService.getToastPiece(loginMember.id(), toastPieceId);
    }

    @DeleteMapping("/{toastPieceId}")
    public void deleteToastPiece(@Login final LoginMember loginMember, final @PathVariable long toastPieceId){
        toastPieceService.deleteToastPieceByMemberIdAndToastPieceId(loginMember.id(), toastPieceId);
    }

}
