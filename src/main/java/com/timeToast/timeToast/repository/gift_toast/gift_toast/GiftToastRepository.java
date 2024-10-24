package com.timeToast.timeToast.repository.gift_toast.gift_toast;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;

import java.util.List;

public interface GiftToastRepository {
    GiftToast save(final GiftToast giftToast);
    List<GiftToast> getGiftToastByMemberId(final long memberId);
    void deleteById(final long giftToastId);
}
