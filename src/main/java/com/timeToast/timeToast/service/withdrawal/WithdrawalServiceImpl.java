package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.team.TeamService;
import org.springframework.transaction.annotation.Transactional;

public class WithdrawalServiceImpl implements WithdrawalService{

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;

    private final GiftToastRepository giftToastRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final FollowRepository followRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamService teamService;

    private final IconMemberRepository iconMemberRepository;

    public WithdrawalServiceImpl(TeamService teamService, MemberRepository memberRepository, GiftToastRepository giftToastRepository, GiftToastOwnerRepository giftToastOwnerRepository, FollowRepository followRepository, TeamMemberRepository teamMemberRepository, MemberTokenRepository memberTokenRepository, IconMemberRepository iconMemberRepository) {
        this.memberRepository = memberRepository;
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.followRepository = followRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.memberTokenRepository = memberTokenRepository;
        this.iconMemberRepository = iconMemberRepository;
        this.teamService = teamService;
    }

    @Transactional
    @Override
    public void memberWithdrawal(final long memberId) {




        teamService.deleteAllTeam(memberId);
        followRepository.deleteAllFollowByMemberId(memberId);
        memberTokenRepository.deleteById(memberId);
        memberRepository.deleteById(memberId);
    }

    @Transactional
    @Override
    public void creatorWithdrawal(final long memberId) {


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
