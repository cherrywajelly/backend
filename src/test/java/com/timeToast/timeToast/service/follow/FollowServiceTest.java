package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.dto.follow.FollowResponse;
import com.timeToast.timeToast.dto.follow.FollowResponses;

import java.util.ArrayList;
import java.util.List;

public class FollowServiceTest implements FollowService {
    @Override
    public void saveFollow(long followingId, long memberId) {
    }

    @Override
    public FollowResponses findFollowerList(long memberId) {
        List<FollowResponse> followResponses = new ArrayList<>();
        followResponses.add(
                FollowResponse.builder().memberId(0).nickname("nickname").memberProfileUrl("memberProfileUrl").build()
        );
        return new FollowResponses(followResponses);
    }

    @Override
    public FollowResponses findFollowingList(long memberId) {
        List<FollowResponse> followResponses = new ArrayList<>();
        followResponses.add(
                FollowResponse.builder().memberId(0).nickname("nickname").memberProfileUrl("memberProfileUrl").build()
        );
        return new FollowResponses(followResponses);
    }

    @Override
    public void deleteFollowing(long followingId, long memberId) {

    }

    @Override
    public void deleteFollower(long memberId, long followerId) {

    }
}
