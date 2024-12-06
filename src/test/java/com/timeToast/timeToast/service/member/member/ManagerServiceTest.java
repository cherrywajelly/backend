package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerServiceTest implements ManagerService{

    @Override
    public MemberManagerResponses getMembersForManagers() {
        List<MemberManagerResponse> memberManagerResponses = new ArrayList<>();
        memberManagerResponses.add(new MemberManagerResponse(1L, "memberProfileUrl", "nickname"));
        return new MemberManagerResponses(memberManagerResponses);
    }

    @Override
    public MemberInfoManagerResponse getMemberInfoForManager(final long memberId){
        return new MemberInfoManagerResponse(1L, "memberProfileUrl", "nickname", "email", LoginType.GOOGLE, PremiumType.PREMIUM);
    }

    @Override
    public FollowManagerResponses getMemberFollowInfo(final long memberId) {
        List<FollowManagerResponse> followManagerResponses = new ArrayList<>();
        followManagerResponses.add(new FollowManagerResponse("followImage", "followNickname"));
        return new FollowManagerResponses(followManagerResponses);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public FollowingManagerResponses getMemberFollowingInfo(final long memberId) {
//        List<FollowingManagerResponse> followingManagerResponses = new ArrayList<>();
//        followingManagerResponses.add(new FollowingManagerResponse("followingImage", "followNickname"));
//        return new FollowingManagerResponses(followingManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public TeamDataManagerResponses getMemberTeamInfo(final long memberId) {
//        List<TeamDataManagerResponse> teamManagerResponses = new ArrayList<>();
//        teamManagerResponses.add(new TeamDataManagerResponse("teamImage", "teamNickname"));
//        return new TeamDataManagerResponses(teamManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId) {
//        List<ShowcaseManagerResponse> showCaseManagerResponses = new ArrayList<>();
//        showCaseManagerResponses.add(new ShowcaseManagerResponse("showImage", "showNickname"));
//        return new ShowcaseManagerResponses(showCaseManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public EventToastDataManagerResponses getMemberEventToastInfo(final long memberId) {
//        List<EventToastDataManagerResponse> eventToastManagerResponses = new ArrayList<>();
//        eventToastManagerResponses.add(new EventToastDataManagerResponse("eventImage", "eventNickname"));
//        return new EventToastDataManagerResponses(eventToastManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId) {
//        List<GiftToastDataManagerResponse> giftToastManagerResponses = new ArrayList<>();
//        giftToastManagerResponses.add(new GiftToastDataManagerResponse("giftImage", "giftNickname"));
//        return new GiftToastDataManagerResponses(giftToastManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public IconGroupManagerResponses getMemberIconGroupInfo(final long memberId) {
//        List<IconGroupManagerResponse> iconGroupManagerResponses = new ArrayList<>();
//        iconGroupManagerResponses.add(new IconGroupManagerResponse("iconname", List.of("iconimages")));
//        return new IconGroupManagerResponses(iconGroupManagerResponses);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId) {
//        List<PaymentManagerResponse> paymentManagerResponses = new ArrayList<>();
//        paymentManagerResponses.add(new PaymentManagerResponse(10, PaymentState.SUCCESS, "orderId", ItemType.ICON, "itemData", LocalDate.of(2024, 1, 1), "nickname", List.of("images"), LocalDate.of(2024,1,1)));
//
//        return new PaymentManagerResponses(paymentManagerResponses);
//    }
//
//    public MemberItemDataResponse createItemData(ItemType itemType, long itemId) {
//        String itemTypeData = "";
//        List<String> images = new ArrayList<>();
//        return new MemberItemDataResponse(itemTypeData, images);
//    }
}
