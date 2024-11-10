package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceMember;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_TOAST_PIECE;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.TOAST_PIECE_NOT_EXISTS;
import static com.timeToast.timeToast.global.constant.FileConstant.*;

@Service
public class ToastPieceServiceImpl implements ToastPieceService{

    private final ToastPieceRepository toastPieceRepository;
    private final ToastPieceImageRepository toastPieceImageRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final FileUploadService fileUploadService;

    public ToastPieceServiceImpl(final ToastPieceRepository toastPieceRepository, final ToastPieceImageRepository toastPieceImageRepository,
                                 final MemberRepository memberRepository, final FileUploadService fileUploadService,
                                 final IconRepository iconRepository) {
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
        this.fileUploadService = fileUploadService;
        this.memberRepository = memberRepository;
        this.iconRepository = iconRepository;
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest) {
        ToastPiece toastPiece = toastPieceRepository.saveToastPiece(ToastPieceRequest.to(memberId, toastPieceRequest));
        return ToastPieceSaveResponse.from(toastPiece, List.of());
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPieceContents(final long memberId, final long toastPieceId, final MultipartFile contents) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new BadRequestException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        if(!toastPiece.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }

        String saveUrl = TOAST_PIECE.value() + SLASH.value() + CONTENTS.value() + SLASH.value() +  toastPiece.getId();
        String toastPieceContentsUrl = fileUploadService.uploadImages(contents, saveUrl);

        toastPiece.updateContentsUrl(toastPieceContentsUrl);

        return ToastPieceSaveResponse.from(toastPiece, List.of());
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPieceImages(final long memberId, final long toastPieceId, final List<MultipartFile> toastPieceImages) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new BadRequestException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        if(!toastPiece.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }

        List<String> toastPieceImageUrls = new ArrayList<>();

        toastPieceImages.forEach(
                toastPieceImage -> {
                    ToastPieceImage saveToastPieceImage = toastPieceImageRepository.save(
                            ToastPieceImage.builder()
                                    .toastPieceId(toastPiece.getId())
                                    .build());

                    String saveUrl = TOAST_PIECE.value() + SLASH.value() + IMAGE.value() + SLASH.value() +  saveToastPieceImage.getId();
                    String toastPieceImageUrl = fileUploadService.uploadImages(toastPieceImage, saveUrl);
                    saveToastPieceImage.updateImageUrl(toastPieceImageUrl);
                    toastPieceImageUrls.add(saveToastPieceImage.getImageUrl());
                }
        );

        return ToastPieceSaveResponse.from(toastPiece, toastPieceImageUrls);
    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceResponses getToastPiecesByGiftToastId(final long giftToastId){
        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToastId);

        toastPieces.forEach(
                toastPiece -> toastPieceResponses.add(getToastPiece(toastPiece.getId()))
        );

        return new ToastPieceResponses(giftToastId, toastPieceResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceResponse getToastPiece(final long toastPieceId) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new BadRequestException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        Optional<Member> member = memberRepository.findById(toastPiece.getMemberId());
        ToastPieceMember toastPieceMember;

        String iconImageUrl = iconRepository.getById(toastPiece.getIconId()).getIconImageUrl();
        List<String> toastPieceImages = new ArrayList<>();

        if(member.isEmpty()){
            toastPieceMember = new ToastPieceMember(0, null, iconRepository.getDefaultIcon().getIconImageUrl());
        }else{
            toastPieceMember = ToastPieceMember.from(member.get());
        }

        toastPieceImageRepository.findAllByToastPieceId(toastPieceId)
                .forEach( toastPieceImage -> toastPieceImages.add(toastPieceImage.getImageUrl()));

        return ToastPieceResponse.from( toastPieceMember,toastPiece, iconImageUrl, toastPieceImages);

    }

    @Transactional
    @Override
    public void deleteToastPieceByMemberIdAndToastPieceId(final long memberId, final long toastPieceId) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new NotFoundException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        if(!toastPiece.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }

        toastPieceImageRepository.deleteAllByToastPieceId(toastPieceId);
        toastPieceRepository.deleteToastPiece(toastPiece);
    }

}
