package com.timeToast.timeToast.controller.follow;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.follow.FollowService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/follows")
@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/followings/{memberId}")
    public void saveFollow(@Login final LoginMember loginMember, @PathVariable final long memberId){
        followService.saveFollow(memberId, loginMember.id());
    }

    @GetMapping("/followings")
    public FollowResponses findFollowingList(@Login final LoginMember loginMember){
        return followService.findFollowingList(loginMember.id());
    }

    @GetMapping("/followers")
    public FollowResponses findFollowerList(@Login final LoginMember loginMember){
        return followService.findFollowerList(loginMember.id());
    }

    @DeleteMapping("/followings/{memberId}")
    public void deleteFollowing(@Login final LoginMember loginMember, @PathVariable final long memberId){
        followService.deleteFollowing(memberId, loginMember.id());
    }

    @DeleteMapping("/followers/{memberId}")
    public void deleteFollower(@Login final LoginMember loginMember, @PathVariable final long memberId){
        followService.deleteFollower(loginMember.id(), memberId);
    }
}
