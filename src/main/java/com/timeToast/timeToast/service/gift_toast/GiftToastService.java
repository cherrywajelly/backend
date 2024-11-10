package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDetailResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastIncompleteResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastSaveResponse;

public interface GiftToastService {
    GiftToastSaveResponse saveGiftToastGroup(final long memberId, final GiftToastGroupRequest giftToastGroupRequest);
    GiftToastSaveResponse saveGiftToastFriend(final long memberId, final GiftToastFriendRequest giftToastFriendRequest);
    GiftToastSaveResponse saveGiftToastMine(final long memberId, final GiftToastMineRequest giftToastMineRequest);
    GiftToastDetailResponse getGiftToastDetail(final long memberId, final long giftToastId);
    GiftToastResponses getGiftToastByMember(final long memberId);
    GiftToastIncompleteResponses getGiftToastIncomplete(final long memberId);
    void deleteGiftToast(final long memberId, final long giftToastId);
}
