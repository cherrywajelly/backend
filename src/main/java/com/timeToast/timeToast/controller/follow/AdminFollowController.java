package com.timeToast.timeToast.controller.follow;

import com.timeToast.timeToast.domain.enums.follow.FollowType;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.service.follow.FollowService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/follows")
@RestController
public class AdminFollowController {

    private final FollowService followService;

    public AdminFollowController(final FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/followers/{memberId}")
    public FollowResponses getFollowers(@PathVariable final long memberId) {
        return followService.findFollowList(memberId, FollowType.FOLLOW);
    }

    @GetMapping("/followings/{memberId}")
    public FollowResponses getFollowings(@PathVariable final long memberId) {
        return followService.findFollowList(memberId, FollowType.FOLLOWING);
    }

}
