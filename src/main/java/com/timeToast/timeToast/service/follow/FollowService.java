package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.dto.follow.response.FollowResponses;

public interface FollowService {

    void saveFollow(final long followingId, final long memberId);
    FollowResponses findFollowerList(final long memberId);
    FollowResponses findFollowingList(final long memberId);
    void deleteFollowing(final long followingMemberId, final long memberId);
    void deleteFollower(final long memberId, final long followerMemberId);

}
