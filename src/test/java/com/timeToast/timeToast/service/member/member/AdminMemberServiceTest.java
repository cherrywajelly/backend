package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
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
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminMemberServiceTest implements AdminMemberService {

    private MemberPremium getMemberPremium() {
        return new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
    }

    private MemberInfoResponse getMemberInfoResponse() {
        return new MemberInfoResponse(1L,"nickname","memberProfileUrl", "email",
                MemberRole.USER, LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveToStaff(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.STAFF, LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveToCreators(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.CREATOR,LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveToUser(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.USER,LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponses getMembersForManagers() {
        List<MemberInfoResponse> memberInfoResponses = new ArrayList<>();
        memberInfoResponses.add(getMemberInfoResponse());
        return new MemberInfoResponses(memberInfoResponses);
    }

    @Override
    public MemberSummaryResponse getMembersCountForManagers() {
        return MemberSummaryResponse.builder()
                .totalUserCount(100)
                .totalCreatorCount(50)
                .build();
    }


    @Override
    public FollowManagerResponses getMemberFollowInfo(final long memberId) {
        List<FollowManagerResponse> followManagerResponses = new ArrayList<>();
        followManagerResponses.add(new FollowManagerResponse("followImage", "followNickname"));
        return new FollowManagerResponses(followManagerResponses);
    }

    @Override
    public FollowingManagerResponses getMemberFollowingInfo(final long memberId) {
        List<FollowingManagerResponse> followingManagerResponses = new ArrayList<>();
        followingManagerResponses.add(new FollowingManagerResponse("followingImage", "followNickname"));
        return new FollowingManagerResponses(followingManagerResponses);
    }

    @Override
    public TeamDataManagerResponses getMemberTeamInfo(final long memberId) {
        List<TeamDataManagerResponse> teamManagerResponses = new ArrayList<>();
        teamManagerResponses.add(new TeamDataManagerResponse("teamImage", "teamNickname"));
        return new TeamDataManagerResponses(teamManagerResponses);
    }

    @Override
    public ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId) {
        List<ShowcaseManagerResponse> showCaseManagerResponses = new ArrayList<>();
        showCaseManagerResponses.add(new ShowcaseManagerResponse("showImage", "showNickname"));
        return new ShowcaseManagerResponses(showCaseManagerResponses);
    }

    @Override
    public EventToastDataManagerResponses getMemberEventToastInfo(final long memberId) {
        List<EventToastDataManagerResponse> eventToastManagerResponses = new ArrayList<>();
        eventToastManagerResponses.add(new EventToastDataManagerResponse("eventImage", "eventNickname"));
        return new EventToastDataManagerResponses(eventToastManagerResponses);
    }

    @Override
    public GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId) {
        List<GiftToastDataManagerResponse> giftToastManagerResponses = new ArrayList<>();
        giftToastManagerResponses.add(new GiftToastDataManagerResponse("giftImage", "giftNickname"));
        return new GiftToastDataManagerResponses(giftToastManagerResponses);
    }

    @Override
    public IconGroupManagerResponses getMemberIconGroupInfo(final long memberId) {
        List<IconGroupManagerResponse> iconGroupManagerResponses = new ArrayList<>();
        iconGroupManagerResponses.add(new IconGroupManagerResponse("iconname", List.of("iconimages")));
        return new IconGroupManagerResponses(iconGroupManagerResponses);
    }

    @Override
    public PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId) {
        List<PaymentManagerResponse> paymentManagerResponses = new ArrayList<>();
        paymentManagerResponses.add(new PaymentManagerResponse(10, PaymentState.SUCCESS, "orderId", ItemType.ICON, "itemData", LocalDate.of(2024, 1, 1), "nickname", List.of("images"), LocalDate.of(2024,1,1)));

        return new PaymentManagerResponses(paymentManagerResponses);
    }
}
