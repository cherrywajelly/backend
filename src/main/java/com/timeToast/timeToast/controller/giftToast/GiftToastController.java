package com.timeToast.timeToast.controller.giftToast;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.giftToast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastDetailResponse;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastIncompleteResponses;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastResponses;
import com.timeToast.timeToast.dto.giftToast.response.GiftToastSaveResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.giftToast.GiftToastService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/giftToasts")
@RestController
public class GiftToastController {

    private final GiftToastService giftToastService;

    public GiftToastController(GiftToastService giftToastService) {
        this.giftToastService = giftToastService;
    }

    @PostMapping("/group")
    public GiftToastSaveResponse saveGiftToastGroup(@Login final LoginMember loginMember, @RequestBody final GiftToastGroupRequest giftToastRequest){
        return giftToastService.saveGiftToastGroup(loginMember.id(), giftToastRequest);
    }

    @PostMapping("/friend")
    public GiftToastSaveResponse saveGiftToastFriend(@Login final LoginMember loginMember, @RequestBody final GiftToastFriendRequest giftToastFriendRequest){
        return giftToastService.saveGiftToastFriend(loginMember.id(), giftToastFriendRequest);
    }

    @PostMapping("/mine")
    public GiftToastSaveResponse saveGiftToastMine(@Login final LoginMember loginMember, @RequestBody final GiftToastMineRequest giftToastMineRequest){
        return giftToastService.saveGiftToastMine(loginMember.id(), giftToastMineRequest);
    }

    @GetMapping("/{giftToastId}")
    public GiftToastDetailResponse getGiftToast(@Login final LoginMember loginMember, @PathVariable final long giftToastId){
        return giftToastService.getGiftToastDetail(loginMember.id(), giftToastId);
    }

    @GetMapping("/members")
    public GiftToastResponses getGiftToastByLogin(@Login final LoginMember loginMember){
        return giftToastService.getGiftToastByMember(loginMember.id());
    }

    @GetMapping("/members/incomplete")
    public GiftToastIncompleteResponses getGiftToastIncomplete(@Login final LoginMember loginMember){
        return giftToastService.getGiftToastIncomplete(loginMember.id());
    }

    @DeleteMapping("/{giftToastId}")
    public Response deleteGiftToast(@Login final LoginMember loginMember, @PathVariable final long giftToastId){
        return giftToastService.deleteGiftToast(loginMember.id(), giftToastId);
    }

}
