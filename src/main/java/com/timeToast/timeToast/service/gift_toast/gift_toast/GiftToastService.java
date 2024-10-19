package com.timeToast.timeToast.service.gift_toast.gift_toast;

import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;

public interface GiftToastService {
    GiftToastResponse saveGiftToast(final long memberId, final GiftToastRequest giftToastRequest);
}
