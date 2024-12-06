package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.follow.response.FollowResponse;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

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
    public Response saveFollow(final long followingId, final long memberId) {
        if(followingId == memberId){
          throw new BadRequestException(INVALID_FOLLOW.getMessage());
        }

        if(followRepository.findByFollowingIdAndFollowerId(followingId,memberId).isPresent()) {
            throw new BadRequestException(FOLLOW_ALREADY_EXISTS.getMessage());
        }

        Follow saveFollow = followRepository.save(
                Follow.builder()
                        .followingId(memberRepository.getById(followingId).getId())
                        .followerId(memberId)
                        .build()
        );
        log.info("save follow {} by {}", saveFollow.getFollowingId(), saveFollow.getFollowerId());

        fcmService.sendMessageTo(
                followingId,
                FcmPostRequest.builder()
                        .fcmConstant(FcmConstant.FOLLOW)
                        .nickname(memberRepository.getById(memberId).getNickname())
                        .param(memberId)
                        .build());

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional(readOnly = true)
    @Override
    public FollowResponses findFollowerList(final long memberId) {
        return getFollowerResponses(followRepository.findAllByFollowingId(memberId));
    }

    @Transactional(readOnly = true)
    @Override
    public FollowResponses findFollowingList(final long memberId) {
        return getFollowingResponses(followRepository.findAllByFollowerId(memberId));
    }

    @Transactional
    @Override
    public Response deleteFollowing(final long followingMemberId, final long memberId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(followingMemberId, memberId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);
        log.info("delete follow {} by {} ", findFollow.getFollowingId(), findFollow.getFollowerId());
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Transactional
    @Override
    public Response deleteFollower(final long memberId, final long followerMemberId) {

        Follow findFollow = followRepository.findByFollowingIdAndFollowerId(memberId, followerMemberId)
                .orElseThrow(() -> new NotFoundException(FOLLOW_NOT_FOUND.getMessage()));

        followRepository.deleteFollow(findFollow);
        log.info("delete follower {} by {}", findFollow.getFollowerId(), findFollow.getFollowingId());
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());

    }

    private FollowResponses getFollowerResponses(final List<Follow> follows) {
        List<FollowResponse> followResponses = new ArrayList<>();

        follows.forEach(
                follow -> {
                    Optional<Member> findMember = memberRepository.findById(follow.getFollowerId());
                    findMember.ifPresent(member -> followResponses.add(FollowResponse.from(member)));

                }
        );
        followResponses.stream().sorted(Comparator.comparing(FollowResponse::nickname));

        return new FollowResponses(followResponses);
    }

    private FollowResponses getFollowingResponses(final List<Follow> follows) {
        List<FollowResponse> followResponses = new ArrayList<>();

        follows.forEach(
                follow -> {
                    Optional<Member> findMember = memberRepository.findById(follow.getFollowingId());
                    findMember.ifPresent(member -> followResponses.add(FollowResponse.from(member)));
                }
        );

        followResponses.stream().sorted(Comparator.comparing(FollowResponse::nickname));
        return new FollowResponses(followResponses);
    }

}
