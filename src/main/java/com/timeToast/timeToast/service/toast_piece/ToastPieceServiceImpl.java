package com.timeToast.timeToast.service.toast_piece;

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
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_TOAST_PIECE;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.TOAST_PIECE_NOT_EXISTS;

@Service
public class ToastPieceServiceImpl implements ToastPieceService{

    private final ToastPieceRepository toastPieceRepository;
    private final ToastPieceImageRepository toastPieceImageRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;

    public ToastPieceServiceImpl(final ToastPieceRepository toastPieceRepository, final ToastPieceImageRepository toastPieceImageRepository,
                                 final GiftToastOwnerRepository giftToastOwnerRepository) {
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest) {
        ToastPiece toastPiece = toastPieceRepository.saveToastPiece(
          ToastPieceRequest.to(memberId, toastPieceRequest)
        );

        System.out.println(toastPiece.toString());

        return ToastPieceSaveResponse.from(toastPiece);
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPieceContents(final long toastPieceId, final MultipartFile contents) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId).orElseThrow(()-> new BadRequestException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        //TODO s3 업로드
        String contentsUrl = "";

        toastPiece.updateContentsUrl(contentsUrl);

        return ToastPieceSaveResponse.from(toastPiece);
    }

    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPieceImages(final long toastPieceId, final List<MultipartFile> toastPieceImages) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId).orElseThrow(()-> new BadRequestException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        //TODO s3 업로드

        return ToastPieceSaveResponse.from(toastPiece);
    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceResponses getToastPiecesByGiftToastId(final long giftToastId){
        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        List<ToastPieceMember> toastPieceMemberList = giftToastOwnerRepository.findToastPieceMemberByGiftToastId(giftToastId);
        List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToastId);

        toastPieces.forEach(
                toastPiece -> {
                    List<ToastPieceImage> toastPieceImages = toastPieceImageRepository.findAllByToastPieceId(toastPiece.getId());

                    toastPieceResponses.add(ToastPieceResponse.from(
                            toastPieceMemberList.stream().filter(toastPieceMember -> toastPieceMember.memberId() == toastPiece.getMemberId()).findFirst().get(),
                            toastPiece,
                            toastPieceImages
                    ));
                }
        );

        return new ToastPieceResponses(giftToastId, toastPieceResponses);
    }

    @Transactional
    @Override
    public void deleteToastPiece(final ToastPiece toastPiece) {
        List<ToastPieceImage> toastPieceImages = toastPieceImageRepository.findAllByToastPieceId(toastPiece.getId());

        toastPieceImages.forEach(toastPieceImage -> toastPieceImageRepository.deleteToastPieceImage(toastPieceImage));
        toastPieceRepository.deleteToastPiece(toastPiece);
    }

    @Transactional
    @Override
    public void deleteToastPieceByMemberId(long memberId, long toastPieceId) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId).orElseThrow(()-> new NotFoundException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        if(toastPiece.getMemberId().equals(memberId)){
            toastPieceRepository.deleteToastPiece(toastPiece);
        }else {
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }


    }

}
