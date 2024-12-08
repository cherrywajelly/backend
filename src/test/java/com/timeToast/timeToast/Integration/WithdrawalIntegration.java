package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.team.TeamService;
import com.timeToast.timeToast.service.withdrawal.WithdrawalService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class WithdrawalIntegration extends TestContainerSupport {

    private final MemberRepository memberRepository;
    private final IconMemberRepository iconMemberRepository;
    private final FollowRepository followRepository;
    private final TeamService teamService;
    private final GiftToastService giftToastService;
    private final EventToastService eventToastService;
    private final ShowcaseRepository showcaseRepository;
    private final FcmRepository fcmRepository;
    private final WithdrawalService withdrawalService;

    @Autowired
    public WithdrawalIntegration(final MemberRepository memberRepository, final IconMemberRepository iconMemberRepository,
                                 final FollowRepository followRepository, final TeamService teamService,
                                 final GiftToastService giftToastService, final EventToastService eventToastService,
                                 final ShowcaseRepository showcaseRepository, final FcmRepository fcmRepository,
                                 final WithdrawalService withdrawalService) {
        this.memberRepository = memberRepository;
        this.iconMemberRepository = iconMemberRepository;
        this.followRepository = followRepository;
        this.teamService = teamService;
        this.giftToastService = giftToastService;
        this.eventToastService = eventToastService;
        this.showcaseRepository = showcaseRepository;
        this.fcmRepository = fcmRepository;
        this.withdrawalService = withdrawalService;
    }

    @Test
    @DisplayName("사용자는 탈퇴할 수 있다.")
    public void tryWithdrawal(){
        Member member = memberRepository.getById(5);
        Assertions.assertFalse(iconMemberRepository.findByMemberId(member.getId()).isEmpty());
        Assertions.assertFalse(followRepository.findAllByFollowerId(member.getId()).isEmpty());
        Assertions.assertFalse(followRepository.findAllByFollowingId(member.getId()).isEmpty());
        Assertions.assertFalse(teamService.findLoginMemberTeams(member.getId()).teamResponses().isEmpty());
        Assertions.assertFalse(giftToastService.getGiftToastByMember(member.getId()).giftToastResponses().isEmpty());
        Assertions.assertFalse(eventToastService.getOwnEventToastList(member.getId()).eventToastOwnResponses().isEmpty());
        Assertions.assertFalse(showcaseRepository.findAllByMemberId(member.getId()).isEmpty());
        Assertions.assertFalse(fcmRepository.findByMemberIdOrderByCreatedAtDesc(member.getId()).isEmpty());

        //withdrawal
        withdrawalService.memberWithdrawal(member.getId());

        Assertions.assertTrue(iconMemberRepository.findByMemberId(member.getId()).isEmpty());
        Assertions.assertTrue(followRepository.findAllByFollowerId(member.getId()).isEmpty());
        Assertions.assertTrue(followRepository.findAllByFollowingId(member.getId()).isEmpty());
        Assertions.assertTrue(teamService.findLoginMemberTeams(member.getId()).teamResponses().isEmpty());
        Assertions.assertTrue(giftToastService.getGiftToastByMember(member.getId()).giftToastResponses().isEmpty());
        Assertions.assertTrue(eventToastService.getEventToasts(member.getId()).eventToastFriendResponses().isEmpty());
        Assertions.assertTrue(showcaseRepository.findAllByMemberId(member.getId()).isEmpty());
        Assertions.assertTrue(fcmRepository.findByMemberIdOrderByCreatedAtDesc(member.getId()).isEmpty());
        Assertions.assertThrows(NotFoundException.class,()->memberRepository.getById(5));


    }
}
