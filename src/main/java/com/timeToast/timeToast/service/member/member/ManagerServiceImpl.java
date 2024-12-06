package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
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
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberItemDataResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
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

    @Transactional(readOnly = true)
    @Override
    public MemberManagerResponses getMembersForManagers() {
        List<MemberManagerResponse> memberManagerResponses = new ArrayList<>();
        List<Member> members = memberRepository.findAllByMemberRole(MemberRole.USER);
        members.forEach(
                member -> {
                    memberManagerResponses.add(MemberManagerResponse.from(member));
                }
        );
        return new MemberManagerResponses(memberManagerResponses);
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
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);
        List<IconGroupManagerResponse> iconGroupManagerResponses = iconGroups.stream()
                .map(iconGroup -> {
                    List<String> iconImages = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream()
                            .map(Icon::getIconImageUrl)
                            .toList();
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
