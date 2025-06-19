package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;

public interface AdminMemberService {
    MemberInfoResponses getMembersForManagers();
    MemberSummaryResponse getMembersCountForManagers();
    FollowManagerResponses getMemberFollowInfo(final long memberId);
    FollowingManagerResponses getMemberFollowingInfo(final long memberId);
    TeamDataManagerResponses getMemberTeamInfo(final long memberId);
    ShowcaseManagerResponses getMemberShowcaseInfo(final long memberId);
    EventToastDataManagerResponses getMemberEventToastInfo(final long memberId);
    GiftToastDataManagerResponses getMemberGiftToastInfo(final long memberId);
    PaymentManagerResponses getMemberPaymentManagerInfo(final long memberId);
}
