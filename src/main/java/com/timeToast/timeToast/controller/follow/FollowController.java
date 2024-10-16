package com.timeToast.timeToast.controller.follow;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.follow.FollowResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.follow.FollowService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/following/{followingId}")
    public void saveFollow(@Login final LoginMember loginMember, @PathVariable final long followingId){
        followService.saveFollow(followingId, loginMember.id());
    }

    @GetMapping("/followings")
    public FollowResponses findFollowingList(@Login final LoginMember loginMember){
        return followService.findFollowingList(loginMember.id());
    }

    @GetMapping("/followers")
    public FollowResponses findFollowerList(@Login final LoginMember loginMember){
        return followService.findFollowerList(loginMember.id());
    }

    @DeleteMapping("/following/{followingId}")
    public void deleteFollowing(@Login final LoginMember loginMember, @PathVariable final long followingId){
        followService.deleteFollowing(followingId, loginMember.id());
    }

    @DeleteMapping("/follower/{followerId}")
    public void deleteFollower(@Login final LoginMember loginMember, @PathVariable final long followerId){
        followService.deleteFollower(loginMember.id(), followerId);
    }
}