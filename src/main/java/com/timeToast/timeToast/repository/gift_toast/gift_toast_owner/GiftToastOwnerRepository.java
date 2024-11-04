package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastOwnerResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceMember;

import java.util.List;

public interface GiftToastOwnerRepository {

    GiftToastOwner save(final GiftToastOwner giftToastOwner);
    List<GiftToastOwner> findAllByGiftToastId(final long giftToastId);
    List<ToastPieceMember> findToastPieceMemberByGiftToastId(final long giftToastId);
    List<GiftToastOwnerResponse> findAllGiftToastMemberByGiftToastId(final long giftToastId);
    boolean checkAllGiftToastOwnerWrote(final long giftToastId);
    void deleteByMemberIdAndGiftToastId(final long memberId, final long giftToastId);
    void delete(final GiftToastOwner giftToastOwner);
}
