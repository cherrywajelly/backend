package com.timeToast.timeToast.repository.gift_toast.gift_toast;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.util.List;
import java.util.Optional;

public interface GiftToastRepository {
    GiftToast save(final GiftToast giftToast);
    Optional<GiftToast> findByGiftToastId(final long giftToastId);
    List<GiftToast> findAllGiftToastsByMemberId(final long memberId);
    List<GiftToast> findAllGiftToastsByMemberIdAndNotOpen(final long memberId);
    List<GiftToast> findAllGiftToastToOpen();
    void deleteById(final long giftToastId);
    GiftToast getById(final long giftToastId);
}
