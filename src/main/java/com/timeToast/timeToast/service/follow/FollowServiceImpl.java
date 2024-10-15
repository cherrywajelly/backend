package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.follow.FollowResponse;
import com.timeToast.timeToast.dto.follow.FollowResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.FOLLOW_ALREADY_EXISTS;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.FOLLOW_NOT_FOUND;

@Service
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public FollowServiceImpl(final FollowRepository followRepository, final MemberRepository memberRepository) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void saveFollow(final long followingId, final long memberId) {
        System.out.println("real service");

        Optional<Follow> findFollow = followRepository.findByFollowingIdAndFollowerId(followingId,memberId);

        if(findFollow.isEmpty()){
            followRepository.save(
                    Follow.builder()
                            .followingId(followingId)
                            .followerId(memberId)
                            .build()
            );
        }else{
            new BadRequestException(FOLLOW_ALREADY_EXISTS.getMessage());
        }
    }

    @Override
    public FollowResponses findFollowerList(final long memberId) {

        List<Follow> follows = followRepository.findAllByFollowingId(memberId);
        return getFollowerResponses(follows);
    }

    @Override
    public FollowResponses findFollowingList(final long memberId) {

        List<Follow> follows = followRepository.findAllByFollowerId(memberId);
        return getFollowingResponses(follows);

    }

    private FollowResponses getFollowingResponses(final List<Follow> follows) {
        List<FollowResponse> followResponses = new ArrayList<>();

        follows.forEach(
                follow -> {
                    Optional<Member> findMember = memberRepository.findById(follow.getFollowingId());
                    findMember.ifPresent(member -> followResponses.add(FollowResponse.from(member)));
                }
        );

        return new FollowResponses(followResponses);
    }

    private FollowResponses getFollowerResponses(final List<Follow> follows) {
        List<FollowResponse> followResponses = new ArrayList<>();

        follows.forEach(
                follow -> {
                    Optional<Member> findMember = memberRepository.findById(follow.getFollowerId());
                    findMember.ifPresent(member -> followResponses.add(FollowResponse.from(member)));

                }
        );

        return new FollowResponses(followResponses);
    }

    @Override
    public void deleteFollowing(final long followingId, final long memberId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(followingId, memberId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);


    }

    @Override
    public void deleteFollower(final long memberId, final long followerId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(memberId, followerId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);

    }


}
