package com.timeToast.timeToast.repository.gift_toast.gift_toast;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.util.List;
import java.util.Optional;

public interface GiftToastRepository {
    GiftToast save(final GiftToast giftToast);
    Optional<GiftToast> findByGiftToastId(final long giftToastId);
    List<GiftToast> getGiftToastByMemberId(final long memberId);
    List<GiftToast> getGiftToastByMemberIdAndNotOpen(final long memberId);
    void deleteById(final long giftToastId);
}
