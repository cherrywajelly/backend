package com.timeToast.timeToast.repository.giftToast.gift_toast_owner;

import com.timeToast.timeToast.domain.giftToast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastOwnerResponse;
import com.timeToast.timeToast.dto.toastPiece.response.ToastPieceMember;

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
