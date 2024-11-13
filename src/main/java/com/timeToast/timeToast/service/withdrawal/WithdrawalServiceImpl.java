package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.team.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final IconMemberRepository iconMemberRepository;
    private final FollowRepository followRepository;
    private final TeamService teamService;
    private final GiftToastService giftToastService;
    private final EventToastService eventToastService;
    private final ShowcaseRepository showcaseRepository;

    public WithdrawalServiceImpl(final MemberRepository memberRepository, final MemberTokenRepository memberTokenRepository,
                                 final IconMemberRepository iconMemberRepository, final FollowRepository followRepository,
                                 final TeamService teamService, final GiftToastService giftToastService,
                                 final EventToastService eventToastService, final ShowcaseRepository showcaseRepository) {

        this.memberRepository = memberRepository;
        this.memberTokenRepository = memberTokenRepository;
        this.iconMemberRepository = iconMemberRepository;
        this.followRepository = followRepository;
        this.teamService = teamService;
        this.giftToastService = giftToastService;
        this.eventToastService = eventToastService;
        this.showcaseRepository = showcaseRepository;
    }

    @Transactional
    @Override
    public void memberWithdrawal(final long memberId) {

        //fcm 추가
        giftToastService.deleteAllGiftToast(memberId);
        showcaseRepository.deleteAllByMemberId(memberId);
        eventToastService.deleteAllEventToastByMemberId(memberId);
        teamService.deleteAllTeam(memberId);
        followRepository.deleteAllFollowByMemberId(memberId);
        iconMemberRepository.deleteAllByMemberId(memberId);
        memberTokenRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    @Transactional
    @Override
    public void creatorWithdrawal(final long memberId) {
        //account 추가
        memberRepository.deleteById(memberId);
    }

    @Transactional
    @Override
    public void adminWithdrawal(final long memberId) {

        memberRepository.deleteById(memberId);
    }

}
