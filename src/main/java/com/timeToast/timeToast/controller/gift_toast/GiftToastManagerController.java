package com.timeToast.timeToast.controller.gift_toast;

import com.timeToast.timeToast.dto.gift_toast.response.GiftToastInfoManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastManagerResponses;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v3/giftToasts")
@RestController
@RequiredArgsConstructor
public class GiftToastManagerController {
    private final GiftToastService giftToastService;

    @GetMapping("")
    public GiftToastManagerResponses getGiftToastManager() {
        return giftToastService.getGiftToastsForManager();
    }

    @GetMapping("/{giftToastId}")
    public GiftToastInfoManagerResponse getGiftToastInfo(@PathVariable final long giftToastId) {
        return giftToastService.getGiftToastInfoforManager(giftToastId);
    }
}
