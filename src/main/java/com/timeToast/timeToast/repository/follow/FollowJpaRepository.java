package com.timeToast.timeToast.repository.follow;

import com.timeToast.timeToast.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFollowingId(final long followingId);
    List<Follow> findAllByFollowerId(final long followerId);
    Optional<Follow> findByFollowingIdAndFollowerId(final long followingId, final long followerId);
}
