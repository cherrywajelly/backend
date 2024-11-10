package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        toastPieceResponses.add(
                new ToastPieceResponse(1, "nickname", "profileUrl", "iconImageUrl", "title", "contentsUrl", LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.of(1,1,1)), List.of("image")));

        return new GiftToastDetailResponse(1, "title", "iconImageUrl", GiftToastType.GROUP, "owner",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), false, 10,
                new ToastPieceResponses(1, toastPieceResponses));
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
    public void deleteGiftToast(long memberId, long giftToastId) {

    }
}