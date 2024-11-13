package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.team.TeamService;
import org.springframework.transaction.annotation.Transactional;

public class WithdrawalServiceImpl implements WithdrawalService{

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final GiftToastService giftToastService;
    private final EventToastService eventToastService;
    private final ShowcaseRepository showcaseRepository;
    private final FollowRepository followRepository;
    private final TeamService teamService;
    private final IconMemberRepository iconMemberRepository;

    public WithdrawalServiceImpl(MemberRepository memberRepository, MemberTokenRepository memberTokenRepository, GiftToastService giftToastService, EventToastService eventToastService, ShowcaseRepository showcaseRepository, FollowRepository followRepository, TeamService teamService, IconMemberRepository iconMemberRepository) {
        this.memberRepository = memberRepository;
        this.memberTokenRepository = memberTokenRepository;
        this.giftToastService = giftToastService;
        this.eventToastService = eventToastService;
        this.showcaseRepository = showcaseRepository;
        this.followRepository = followRepository;
        this.teamService = teamService;
        this.iconMemberRepository = iconMemberRepository;
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
        memberTokenRepository.deleteById(memberId);
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


    private void deleteMember(final long memberId){


    }
}
