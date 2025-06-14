package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.domain.giftToast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.giftToast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.timeToast.timeToast.domain.enums.fcm.FcmConstant.GIFTTOASTBAKED;
import static com.timeToast.timeToast.global.constant.BasicImage.BASIC_PROFILE_IMAGE_URL;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
public class ToastPieceServiceImpl implements com.timeToast.timeToast.service.toast_piece.ToastPieceService {

    private final ToastPieceRepository toastPieceRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final GiftToastRepository giftToastRepository;
    private final FcmService fcmService;
    private final IconRepository iconRepository;

    public ToastPieceServiceImpl(final ToastPieceRepository toastPieceRepository,
                                 final MemberRepository memberRepository, final FileUploadService fileUploadService,
                                 final GiftToastOwnerRepository giftToastOwnerRepository, final IconRepository iconRepository,
                                 final FcmService fcmService, final GiftToastRepository giftToastRepository) {
        this.toastPieceRepository = toastPieceRepository;
        this.fileUploadService = fileUploadService;
        this.memberRepository = memberRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.fcmService = fcmService;
        this.giftToastRepository = giftToastRepository;
        this.iconRepository = iconRepository;

    }

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;


    @Transactional
    @Override
    public ToastPieceSaveResponse saveToastPiece(final long memberId, final ToastPieceRequest toastPieceRequest,
                                                 final MultipartFile contents, final List<MultipartFile> toastPieceImages) {
        if(toastPieceTitleValidation(toastPieceRequest)) {
            throw new BadRequestException(INVALID_STRING_FORMAT.getMessage());
        }

        ToastPiece toastPiece = toastPieceRepository.saveToastPiece(ToastPieceRequest.to(memberId, toastPieceRequest));

        saveToastPieceImages(toastPiece, toastPieceImages);
        toastPiece.updateContentsUrl(saveToastPieceContents(toastPiece, contents));
        sendMessage(memberId, toastPiece);

        return ToastPieceSaveResponse.from(toastPiece);
    }

    private static boolean toastPieceTitleValidation(ToastPieceRequest toastPieceRequest) {
        return toastPieceRequest.title().length() > 20;
    }

    private void sendMessage(long memberId, ToastPiece toastPiece) {
        List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(toastPiece.getGiftToastId());
        List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(toastPiece.getGiftToastId());
        GiftToast giftToast = giftToastRepository.getById(toastPiece.getGiftToastId());

        if(isToastPieces(giftToastOwners, toastPieces) && isOpenDate(giftToast)) {
            giftToast.updateIsOpened(true);
        }
        giftToastOwners.forEach(giftToastOwner -> {

            if(!giftToastOwner.getMemberId().equals(memberId)){
                fcmService.sendMessageTo(giftToastOwner.getMemberId(),
                        FcmPostRequest.builder()
                                .fcmConstant(GIFTTOASTBAKED)
                                .senderId(memberId)
                                .toastName(giftToast.getTitle())
                                .param(giftToast.getId())
                                .build());
            }
        });
    }

    private static boolean isToastPieces(List<GiftToastOwner> giftToastOwners, List<ToastPiece> toastPieces) {
        return giftToastOwners.stream().allMatch(giftToastOwner ->
                toastPieces.stream().anyMatch(toast -> toast.getMemberId().equals(giftToastOwner.getMemberId())));
    }

    private static boolean isOpenDate(GiftToast giftToast){
        return (giftToast.getOpenedDate().isEqual(LocalDate.now()) || giftToast.getOpenedDate().isAfter(LocalDate.now()));
    }

    private String saveToastPieceContents(final ToastPiece toastPiece, final MultipartFile contents ) {

        String saveUrl = baseUrl + TOAST_PIECE.value() + SLASH.value() + CONTENTS.value() + SLASH.value() +  toastPiece.getId();
        return fileUploadService.uploadfile(contents, saveUrl);
    }

    private void saveToastPieceImages(ToastPiece toastPiece, List<MultipartFile> toastPieceImages) {

        List<ToastPieceImage> uploadedToastPieceImages = new ArrayList<>();

        toastPieceImages.forEach(
                toastPieceImage -> {
                    ToastPieceImage saveToastPieceImage = ToastPieceImage.builder().build();

                    String saveUrl = baseUrl + TOAST_PIECE.value() + SLASH.value() +  toastPiece.getId()
                            + RandomStringUtils.randomAlphanumeric(10) + SLASH.value() + IMAGE.value() ;
                    String toastPieceImageUrl = fileUploadService.uploadfile(toastPieceImage, saveUrl);

                    saveToastPieceImage.updateImageUrl(toastPieceImageUrl);
                    uploadedToastPieceImages.add(saveToastPieceImage);
                }
        );

        toastPiece.addToastPieceImages(uploadedToastPieceImages);

    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceResponses getToastPiecesByGiftToastId(final long giftToastId){
        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();

        toastPieceRepository.findAllByGiftToastId(giftToastId).forEach(
                toastPiece -> toastPieceResponses.add(getToastPieceResponse(toastPiece.getId())));

        return new ToastPieceResponses(giftToastId, toastPieceResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceResponse getToastPieceResponse(final long toastPieceId){
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new NotFoundException(TOAST_PIECE_NOT_FOUND.getMessage()));

        Optional<Member> member = memberRepository.findById(toastPiece.getMemberId());
        ToastPieceMember toastPieceMember;
        if(member.isEmpty()){
            toastPieceMember = new ToastPieceMember(null, null, BASIC_PROFILE_IMAGE_URL);
        }else{
            toastPieceMember = ToastPieceMember.from(member.get());
        }

        String iconImageUrl = iconRepository.getById(toastPiece.getIconId()).getIconImageUrl();
        List<String> toastPieceImages = toastPiece.getToastPieceImages()
                .stream()
                .map(ToastPieceImage::getImageUrl)
                .collect(Collectors.toList());

        return ToastPieceResponse.from(toastPieceMember,toastPiece, iconImageUrl, toastPieceImages);
    }

    @Transactional
    @Override
    public Response deleteToastPieceByMemberIdAndToastPieceId(final long memberId, final long toastPieceId) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new NotFoundException(TOAST_PIECE_NOT_EXISTS.getMessage()));

        if(!toastPiece.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_TOAST_PIECE.getMessage());
        }

        log.info("delete toastPiece contents {} by {}", toastPiece.getId(), memberId);

        toastPieceRepository.deleteToastPiece(toastPiece);

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

}
