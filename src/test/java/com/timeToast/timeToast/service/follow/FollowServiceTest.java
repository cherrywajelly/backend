package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.dto.follow.response.FollowResponse;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class FollowServiceTest implements FollowService {
    @Override
    public Response saveFollow(long followingId, long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
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
    public Response deleteFollowing(long followingMemberId, long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Override
    public Response deleteFollower(long memberId, long followerMemberId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}
