package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.jpa.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jpa.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import com.timeToast.timeToast.repository.jpa.payment.PaymentRepository;
import com.timeToast.timeToast.repository.jpa.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.jpa.team.team.TeamRepository;
import com.timeToast.timeToast.repository.jpa.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PaymentRepository paymentRepository;
    private final TeamRepository teamRepository;
    private final ShowcaseRepository showcaseRepository;
    private final EventToastRepository eventToastRepository;
    private final GiftToastRepository giftToastRepository;
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberService memberService;
    private final RedisService redisService;


    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponses getMembersForManagers() {
        List<Member> members = memberRepository.findAllByMemberRole(MemberRole.USER);
        List<MemberInfoResponse> memberInfos = members.stream()
                .map(member ->
                        MemberInfoResponse.from(member, memberService.getMemberPremiumByMember(member)))
                .collect(Collectors.toList());

        return new MemberInfoResponses(memberInfos);
    }

    @Override
    public MemberSignUpInfo getMemberSignUpInfo() {
        return redisService.getTotalSignUp();
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
        String itemTypeData;
        List<String> images = new ArrayList<>();
        IconGroup iconGroup = iconGroupRepository.getById(itemId);

        if (itemType.equals(ItemType.PREMIUM)) {
            itemTypeData = "PREMIUM";
        }
        else {
            itemTypeData = iconGroup.getName();
            iconGroup.getIcons().forEach(
                    icon -> images.add(icon.getIconImageUrl())
            );
        }
        return new MemberItemDataResponse(itemTypeData, images);
    }
}
