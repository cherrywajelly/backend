package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftToastServiceTest implements GiftToastService{

    @Override
    public GiftToastSaveResponse saveGiftToastGroup(long memberId, GiftToastGroupRequest giftToastGroupRequest) {
        return new GiftToastSaveResponse(1, "title", GiftToastType.GROUP, LocalDate.of(2024, 1, 1),LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastSaveResponse saveGiftToastFriend(long memberId, GiftToastFriendRequest giftToastFriendRequest) {
        return new GiftToastSaveResponse(1, "title", GiftToastType.FRIEND, LocalDate.of(2024, 1, 1),LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastSaveResponse saveGiftToastMine(long memberId, GiftToastMineRequest giftToastMineRequest) {
        return new GiftToastSaveResponse(1, "title", GiftToastType.MINE, LocalDate.of(2024, 1, 1),LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastDetailResponse getGiftToastDetail(long memberId, long giftToastId) {

        GiftToastInfo giftToastInfo =  GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .isOpened(false)
                .build();

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

        return new GiftToastDetailResponse(giftToastInfo,1L, new ToastPieceResponses(1L,toastPieceResponses) );
    }

    @Override
    public GiftToastInfo getGiftToastInfo(final long memberId, final GiftToast giftToast) {
        return GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .isOpened(false)
                .build();
    }

    @Override
    public GiftToastResponses getGiftToastByMember(long memberId) {
        List<GiftToastResponse> giftToastResponses = new ArrayList<>();
        giftToastResponses.add(
                new GiftToastResponse(1, "title", "iconImageUrl", GiftToastType.GROUP, "owner", false)
        );
        return new GiftToastResponses(giftToastResponses);
    }

    @Override
    public GiftToastIncompleteResponses getGiftToastIncomplete(long memberId) {
        List<GiftToastIncompleteResponse> giftToastResponses = new ArrayList<>();
        giftToastResponses.add(
                new GiftToastIncompleteResponse(1, "title", "iconImageUrl")
        );
        return new GiftToastIncompleteResponses(giftToastResponses);
    }

    @Override
    public ToastPieceDetailResponse getToastPiece(long memberId, long toastPieceId) {
        GiftToastInfo giftToastInfo =  GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .isOpened(false)
                .build();

        ToastPieceResponse toastPieceResponse =  ToastPieceResponse.builder()
                .memberId(1L)
                .toastPieceId(1L)
                .nickname("nickname")
                .profileUrl("profileUrl")
                .iconImageUrl("iconImageUrl")
                .title("title")
                .contentsUrl("contentsUrl")
                .createdAt(LocalDate.now())
                .toastPieceImages(List.of("images"))
                .build();

        return new ToastPieceDetailResponse(giftToastInfo,toastPieceResponse);
    }

    @Override
    public void deleteGiftToast(long memberId, long giftToastId) {

    }
}