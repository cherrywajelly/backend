package com.timeToast.timeToast.service.gift_toast.gift_toast;

import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponses;

public interface GiftToastService {
    GiftToastResponse saveGiftToast(final long memberId, final GiftToastRequest giftToastRequest);

    GiftToastResponses getGiftToast(final long memberId);
    GiftToastResponses getGiftToastIncomplete(final long memberId);

    void deleteGiftToast(final long memberId, final long giftToastId);
}
