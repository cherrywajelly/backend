package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberAdminResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberSummaryResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;

public interface ManagerService {
    MemberAdminResponse saveToStaff(final long memberId);
    MemberAdminResponse saveToCreators(final long memberId);
    MemberAdminResponse saveToUser(final long memberId);
    MemberManagerResponses getMembersForManagers();
    MemberSummaryResponse getMembersCountForManagers();
    MemberInfoManagerResponse getMemberInfoForManager(final long memberId);
    FollowManagerResponses getMemberFollowInfo(final long memberId);
    FollowingManagerResponses getMemberFollowingInfo(final long memberId);
    TeamDataManagerResponses getMemberTeamInfo(final long memberId);
    ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId);
    EventToastDataManagerResponses getMemberEventToastInfo(final long memberId);
    GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId);
    IconGroupManagerResponses getMemberIconGroupInfo(final long memberId);
    PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId);
}
