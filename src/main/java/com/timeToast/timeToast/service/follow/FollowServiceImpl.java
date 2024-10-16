package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.follow.FollowResponse;
import com.timeToast.timeToast.dto.follow.FollowResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.FOLLOW_ALREADY_EXISTS;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.FOLLOW_NOT_FOUND;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public FollowServiceImpl(final FollowRepository followRepository, final MemberRepository memberRepository) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void saveFollow(final long followingId, final long memberId) {
        Optional<Follow> findFollow = followRepository.findByFollowingIdAndFollowerId(followingId,memberId);

        if(findFollow.isEmpty()){
            Follow saveFollow = followRepository.save(
                    Follow.builder()
                            .followingId(followingId)
                            .followerId(memberId)
                            .build()
            );
            log.info("save follow {} by {}", saveFollow.getFollowingId(), saveFollow.getFollowerId());
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
        log.info("delete follow {} by {} ", findFollow.getFollowingId(), findFollow.getFollowerId());


    }

    @Override
    public void deleteFollower(final long memberId, final long followerId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(memberId, followerId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);
        log.info("delete follower {} by {}", findFollow.getFollowerId(), findFollow.getFollowingId());

    }


}