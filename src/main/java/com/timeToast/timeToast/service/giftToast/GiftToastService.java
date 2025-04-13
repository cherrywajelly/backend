package com.timeToast.timeToast.service.giftToast;

import com.timeToast.timeToast.domain.giftToast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.giftToast.response.*;
import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.global.response.Response;

public interface GiftToastService {
    GiftToastSaveResponse saveGiftToastGroup(final long memberId, final GiftToastGroupRequest giftToastGroupRequest);
    GiftToastSaveResponse saveGiftToastFriend(final long memberId, final GiftToastFriendRequest giftToastFriendRequest);
    GiftToastSaveResponse saveGiftToastMine(final long memberId, final GiftToastMineRequest giftToastMineRequest);
    GiftToastDetailResponse getGiftToastDetail(final long memberId, final long giftToastId);
    GiftToastInfo getGiftToastInfo(final long memberId, final GiftToast giftToast);
    GiftToastResponses getGiftToastByMember(final long memberId);
    GiftToastIncompleteResponses getGiftToastIncomplete(final long memberId);
    ToastPieceDetailResponse getToastPiece(final long memberId, final long toastPieceId);
    Response deleteGiftToast(final long memberId, final long giftToastId);
    void deleteAllGiftToast(long memberId);
    GiftToastManagerResponses getGiftToastsForManager();
    GiftToastInfoManagerResponse getGiftToastInfoForManager(final long giftToastId);
    GiftToastRequest editGiftToast(final long giftToastId, final GiftToastRequest giftToastRequest);
}
