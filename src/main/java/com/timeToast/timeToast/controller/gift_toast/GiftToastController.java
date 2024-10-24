package com.timeToast.timeToast.controller.gift_toast;


import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastIncompleteResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponses;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/giftToasts/")
@RestController
public class GiftToastController {

    private final GiftToastService giftToastService;

    public GiftToastController(GiftToastService giftToastService) {
        this.giftToastService = giftToastService;
    }

    @PostMapping("")
    public GiftToastResponse saveGiftToast(@Login final LoginMember loginMember, @RequestBody final GiftToastRequest giftToastRequest){
        return giftToastService.saveGiftToast(loginMember.id(), giftToastRequest);
    }

    @GetMapping("members")
    public GiftToastResponses getGiftToast(@Login final LoginMember loginMember){
        return giftToastService.getGiftToast(loginMember.id());
    }

    @GetMapping("members/incomplete")
    public GiftToastIncompleteResponses getGiftToastIncomplete(@Login final LoginMember loginMember){
        return giftToastService.getGiftToastIncomplete(loginMember.id());
    }



    @DeleteMapping("{giftToastId}")
    public void deleteGiftToast(@Login final LoginMember loginMember, @PathVariable final long giftToastId){
        giftToastService.deleteGiftToast(loginMember.id(), giftToastId);
    }

}
