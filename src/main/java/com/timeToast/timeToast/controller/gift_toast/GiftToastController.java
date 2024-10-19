package com.timeToast.timeToast.controller.gift_toast;


import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.gift_toast.gift_toast.GiftToastService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/giftToast")
@RestController
public class GiftToastController {

    private final GiftToastService giftToastService;

    public GiftToastController(final GiftToastService giftToastService) {
        this.giftToastService = giftToastService;
    }

    @PostMapping("")
    public GiftToastResponse saveGiftToast(@Login final LoginMember loginMember, @RequestBody final GiftToastRequest giftToastRequest){
        return giftToastService.saveGiftToast(loginMember.id(), giftToastRequest);
    }
}
