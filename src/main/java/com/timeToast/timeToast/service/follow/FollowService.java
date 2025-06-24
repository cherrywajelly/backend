package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.enums.follow.FollowType;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.response.Response;

public interface FollowService {
    Response saveFollow(final long followingId, final long memberId);
    FollowResponses findFollowList(final long memberId, final FollowType followType);
    Response deleteFollowing(final long followingMemberId, final long memberId);
    Response deleteFollower(final long memberId, final long followerMemberId);

}
