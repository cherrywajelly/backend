package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastOwnerResponse;

import java.util.List;

public interface GiftToastOwnerRepository {

    GiftToastOwner save(final GiftToastOwner giftToastOwner);

    List<GiftToastOwner> findByGiftToastId(final long giftToastId);
    List<GiftToastOwnerResponse> findGiftToastOwnerResponsesByGiftToastId(final long giftToastId);

    void delete(final GiftToastOwner giftToastOwner);
}
