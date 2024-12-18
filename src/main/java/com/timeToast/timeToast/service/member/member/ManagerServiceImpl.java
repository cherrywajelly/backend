package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final FollowRepository followRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PremiumRepository premiumRepository;
    private final PaymentRepository paymentRepository;
    private final TeamRepository teamRepository;
    private final ShowcaseRepository showcaseRepository;
    private final EventToastRepository eventToastRepository;
    private final GiftToastRepository giftToastRepository;
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final IconMemberRepository iconMemberRepository;

    @Transactional
    @Override
    public MemberAdminResponse saveToStaff(final long memberId) {
        Member member = updateRole(memberId, MemberRole.STAFF);
        return MemberAdminResponse.from(member);
    }

    @Transactional
    @Override
    public MemberAdminResponse saveToCreators(final long memberId) {
        Member member = updateRole(memberId, MemberRole.CREATOR);
        return MemberAdminResponse.from(member);
    }

    @Transactional
    @Override
    public MemberAdminResponse saveToUser(final long memberId) {
        Member member = updateRole(memberId, MemberRole.USER);
        return MemberAdminResponse.from(member);
    }

    private Member updateRole(final long memberId, final MemberRole role) {
        Member member = memberRepository.getById(memberId);
        member.updateMemberRole(role);
        return member;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberManagerResponses getMembersForManagers() {
        List<MemberInfoManagerResponse> memberManagerResponses = new ArrayList<>();
        List<Member> members = memberRepository.findAllByMemberRole(MemberRole.USER);
        members.forEach(
                member -> {
                    Premium premium = premiumRepository.getById(member.getPremiumId());
                    memberManagerResponses.add(MemberInfoManagerResponse.from(member, premium.getPremiumType()));
                }
        );
        return new MemberManagerResponses(memberManagerResponses);
    }

    @Override
    public MemberSummaryResponse getMembersCountForManagers() {
        return MemberSummaryResponse.builder()
                .totalUserCount(memberRepository.findAllByMemberRole(MemberRole.USER).stream().count())
                .totalCreatorCount(memberRepository.findAllByMemberRole(MemberRole.CREATOR).stream().count())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfoManagerResponse getMemberInfoForManager(final long memberId) {
        Member member = memberRepository.getById(memberId);
        Premium premium = premiumRepository.getById(member.getPremiumId());

        return MemberInfoManagerResponse.from(member, premium.getPremiumType());
    }

    @Transactional(readOnly = true)
    @Override
    public FollowManagerResponses getMemberFollowInfo(final long memberId) {
        List<FollowManagerResponse> followManagerResponses = followRepository.findAllByFollowingId(memberId).stream()
                .map(follow -> memberRepository.getById(follow.getFollowerId()))
                .map(FollowManagerResponse::from)
                .toList();
        return new FollowManagerResponses(followManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public FollowingManagerResponses getMemberFollowingInfo(final long memberId) {
        List<FollowingManagerResponse> followingManagerResponses = followRepository.findAllByFollowerId(memberId).stream()
                .map(follow -> memberRepository.getById(follow.getFollowingId()))
                .map(FollowingManagerResponse::from)
                .toList();
        return new FollowingManagerResponses(followingManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public TeamDataManagerResponses getMemberTeamInfo(final long memberId) {
        List<TeamDataManagerResponse> teamManagerResponses = teamMemberRepository.findAllByMemberId(memberId).stream()
                .map(teamMember -> TeamDataManagerResponse.from(teamRepository.getById(teamMember.getTeamId())))
                .toList();
        return new TeamDataManagerResponses(teamManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId) {
        List<ShowcaseManagerResponse> showcaseManagerResponses = showcaseRepository.findAllByMemberId(memberId).stream()
                .map(showcase -> ShowcaseManagerResponse.from(
                        iconRepository.getById(eventToastRepository.getById(showcase.getEventToastId()).getIconId()).getIconImageUrl(),
                        eventToastRepository.getById(showcase.getEventToastId()).getTitle()
                ))
                .toList();
        return new ShowcaseManagerResponses(showcaseManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastDataManagerResponses getMemberEventToastInfo(final long memberId) {
        List<EventToastDataManagerResponse> eventToastManagerResponses = eventToastRepository.findAllByMemberId(memberId).stream()
                .map(eventToast -> EventToastDataManagerResponse.from(
                        iconRepository.getById(eventToast.getIconId()).getIconImageUrl(),
                        eventToast.getTitle()
                ))
                .toList();
        return new EventToastDataManagerResponses(eventToastManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId) {
        List<GiftToastDataManagerResponse> giftToastManagerResponses = giftToastRepository.findAllGiftToastsByMemberId(memberId).stream()
                .map(giftToast -> GiftToastDataManagerResponse.from(
                        iconRepository.getById(giftToast.getIconId()).getIconImageUrl(),
                        giftToast.getTitle()
                ))
                .toList();
        return new GiftToastDataManagerResponses(giftToastManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupManagerResponses getMemberIconGroupInfo(final long memberId) {
        List<IconMember> iconMembers = iconMemberRepository.findByMemberId(memberId);
        List<IconGroupManagerResponse> iconGroupManagerResponses = iconMembers.stream()
                .map(iconMember -> {
                    List<String> iconImages = iconRepository.findAllByIconGroupId(iconMember.getIconGroupId()).stream()
                            .map(Icon::getIconImageUrl)
                            .toList();
                    IconGroup iconGroup = iconGroupRepository.getById(iconMember.getIconGroupId());
                    return IconGroupManagerResponse.from(iconGroup.getName(), iconImages);
                })
                .toList();
        return new IconGroupManagerResponses(iconGroupManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId) {
        Member member = memberRepository.getById(memberId);

        List<PaymentManagerResponse> paymentManagerResponses = new ArrayList<>();
        List<Payment> payments = paymentRepository.findByMemberId(memberId);
        payments.forEach(
                payment -> {
                    MemberItemDataResponse memberItemDataResponse = createItemData(payment.getItemType(), payment.getItemId());
                    paymentManagerResponses.add(PaymentManagerResponse.from(payment, memberItemDataResponse.itemTypeData(), member.getNickname(), memberItemDataResponse.images()));
                }
        );
        return new PaymentManagerResponses(paymentManagerResponses);
    }


    public MemberItemDataResponse createItemData(ItemType itemType, long itemId) {
        String itemTypeData = "";
        List<String> images = new ArrayList<>();
        IconGroup iconGroup = iconGroupRepository.getById(itemId);

        if (itemType.equals(ItemType.PREMIUM)) {
            itemTypeData = "PREMIUM";
        }
        else {
            itemTypeData = iconGroup.getName();
            List<Icon> icons = iconRepository.findAllByIconGroupId(iconGroup.getId());
            icons.forEach(
                    icon -> {
                        images.add(icon.getIconImageUrl());
                    }
            );
        }
        return new MemberItemDataResponse(itemTypeData, images);
    }
}
