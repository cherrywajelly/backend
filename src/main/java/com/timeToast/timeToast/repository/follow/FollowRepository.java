package com.timeToast.timeToast.repository.follow;

import com.timeToast.timeToast.domain.follow.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {

    Follow save(final Follow follow);
    List<Follow> findAllByFollowingId(final long followingId);
    List<Follow> findAllByFollowerId(final long followerId);
    Optional<Follow> findByFollowingIdAndFollowerId(final long followingId, final long followerId);
    void deleteFollow(final Follow follow);
}
