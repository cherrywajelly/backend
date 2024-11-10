package com.timeToast.timeToast.controller.toast_piece;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RequestMapping("/api/v1/toastPieces")
@RestController
public class ToastPieceController {

    private final ToastPieceService toastPieceService;
    public ToastPieceController(final ToastPieceService toastPieceService) {
        this.toastPieceService = toastPieceService;
    }

    @PostMapping("")
    public ToastPieceSaveResponse saveToastPiece(@Login final LoginMember loginMember, @RequestBody final ToastPieceRequest toastPieceRequest){
        return toastPieceService.saveToastPiece(loginMember.id(), toastPieceRequest);
    }

    @PostMapping("/{toastPieceId}/contents")
    public ToastPieceSaveResponse saveToastPieceContents(@Login LoginMember loginMember, @PathVariable final long toastPieceId, @RequestPart final MultipartFile toastPieceContents){
        return toastPieceService.saveToastPieceContents(loginMember.id(), toastPieceId, toastPieceContents);
    }

    @PostMapping("/{toastPieceId}/images")
    public ToastPieceSaveResponse saveToastPieceImages(@Login LoginMember loginMember, @PathVariable final long toastPieceId, @RequestPart final List<MultipartFile> toastPieceImages){
        return toastPieceService.saveToastPieceImages(loginMember.id(), toastPieceId, toastPieceImages);
    }

    @GetMapping("/{toastPieceId}")
    public ToastPieceResponse getToastPieceDetail(final @PathVariable long toastPieceId){
        return toastPieceService.getToastPiece(toastPieceId);
    }

    @DeleteMapping("/{toastPieceId}")
    public void deleteToastPiece(@Login final LoginMember loginMember, final @PathVariable long toastPieceId){
        toastPieceService.deleteToastPieceByMemberIdAndToastPieceId(loginMember.id(), toastPieceId);
    }

}
