package com.timeToast.timeToast.repository.follow;

import com.timeToast.timeToast.domain.follow.Follow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FollowRepositoryImpl implements FollowRepository{

    private final FollowJpaRepository followJpaRepository;

    public FollowRepositoryImpl(final FollowJpaRepository followJpaRepository) {
        this.followJpaRepository = followJpaRepository;
    }

    @Override
    public Follow save(final Follow follow){
        return followJpaRepository.save(follow);
    }

    @Override
    public Optional<Follow> findById(long followId) {
        return followJpaRepository.findById(followId);
    }

    @Override
    public List<Follow> findAllByFollowingId(final long followingId){
        return followJpaRepository.findAllByFollowingId(followingId);
    }

    @Override
    public List<Follow> findAllByFollowerId(final long followerId){
        return followJpaRepository.findAllByFollowerId(followerId);
    }

    @Override
    public Optional<Follow> findByFollowingIdAndFollowerId(final long followingId, final long followerId) {
        return followJpaRepository.findByFollowingIdAndFollowerId(followingId, followerId);
    }

    @Override
    public void deleteFollow(final Follow follow) {
        followJpaRepository.delete(follow);
    }


}
