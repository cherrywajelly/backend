package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberItemDataResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import org.springframework.stereotype.Service;

public interface ManagerService {
    MemberManagerResponses getMembersForManagers();
    MemberInfoManagerResponse getMemberInfoForManager(final long memberId);
    FollowManagerResponses getMemberFollowInfo(final long memberId);
    FollowingManagerResponses getMemberFollowingInfo(final long memberId);
    TeamDataManagerResponses getMemberTeamInfo(final long memberId);
//    ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId);
//    EventToastDataManagerResponses getMemberEventToastInfo(final long memberId);
//    GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId);
//    IconGroupManagerResponses getMemberIconGroupInfo(final long memberId);
//    PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId);
//    MemberItemDataResponse createItemData(ItemType itemType, long itemId);
}
