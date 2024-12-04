package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.transaction.annotation.Transactional;

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
}
