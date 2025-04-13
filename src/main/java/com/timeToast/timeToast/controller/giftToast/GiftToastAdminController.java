package com.timeToast.timeToast.controller.giftToast;

import com.timeToast.timeToast.dto.giftToast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastInfoManagerResponse;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastManagerResponses;
import com.timeToast.timeToast.service.giftToast.GiftToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/giftToasts")
@RestController
@RequiredArgsConstructor
public class GiftToastAdminController {
    private final GiftToastService giftToastService;

    @GetMapping("")
    public GiftToastManagerResponses getGiftToastManager() {
        return giftToastService.getGiftToastsForManager();
    }

    @GetMapping("/{giftToastId}")
    public GiftToastInfoManagerResponse getGiftToastInfo(@PathVariable final long giftToastId) {
        return giftToastService.getGiftToastInfoForManager(giftToastId);
    }

    @PutMapping("/{giftToastId}")
    public GiftToastRequest editGiftToast(@PathVariable final long giftToastId, @RequestBody GiftToastRequest giftToastRequest) {
        return giftToastService.editGiftToast(giftToastId, giftToastRequest);
    }
}
