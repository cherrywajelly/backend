package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.dto.follow.response.FollowResponse;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class FollowServiceTest implements FollowService {
    @Override
    public Response saveFollow(long followingId, long memberId) {
        if(followingId == memberId || followingId == 3) {
            throw new BadRequestException(INVALID_FOLLOW.getMessage());
        }
        if(followingId==4){
            throw new BadRequestException(FOLLOW_ALREADY_EXISTS.getMessage());
        }
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
    public Response deleteFollowing(final long followingMemberId, final  long memberId) {
        if(followingMemberId == 2){
            throw new NotFoundException(FOLLOW_NOT_FOUND.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Override
    public Response deleteFollower(long memberId, long followerMemberId) {
        if(followerMemberId == 2){
            throw new NotFoundException(FOLLOW_NOT_FOUND.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}
