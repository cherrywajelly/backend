package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.follow.response.FollowResponse;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final FcmService fcmService;

    public FollowServiceImpl(final FollowRepository followRepository, final MemberRepository memberRepository, final FcmService fcmService) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository;
        this.fcmService = fcmService;
    }
    @Transactional
    @Override
    public void saveFollow(final long followingId, final long memberId) {
        Optional<Follow> findFollow = followRepository.findByFollowingIdAndFollowerId(followingId,memberId);

        if(findFollow.isEmpty()){
            Member followingMember = memberRepository.findById(followingId).orElseThrow(()-> new BadRequestException(MEMBER_NOT_EXISTS.getMessage()));
            Follow saveFollow = followRepository.save(
                    Follow.builder()
                            .followingId(followingMember.getId())
                            .followerId(memberId)
                            .build()
            );
            log.info("save follow {} by {}", saveFollow.getFollowingId(), saveFollow.getFollowerId());


            fcmService.sendMessageTo(
                    followingId,
                    FcmResponse.builder()
                            .fcmConstant(FcmConstant.FOLLOW)
                            .nickname(memberRepository.getById(memberId).getNickname())
                            .param(memberId)
                    .build());
        }else{
            throw new BadRequestException(FOLLOW_ALREADY_EXISTS.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FollowResponses findFollowerList(final long memberId) {

        List<Follow> follows = followRepository.findAllByFollowingId(memberId);
        return getFollowerResponses(follows);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public void deleteFollowing(final long followingMemberId, final long memberId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(followingMemberId, memberId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);
        log.info("delete follow {} by {} ", findFollow.getFollowingId(), findFollow.getFollowerId());


    }

    @Transactional
    @Override
    public void deleteFollower(final long memberId, final long followerMemberId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(memberId, followerMemberId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);
        log.info("delete follower {} by {}", findFollow.getFollowerId(), findFollow.getFollowingId());

    }


}
