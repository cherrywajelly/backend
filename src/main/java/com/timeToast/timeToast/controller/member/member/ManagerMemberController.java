package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.service.member.member.ManagerService;
import com.timeToast.timeToast.service.member.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v3/members")
@RestController
@RequiredArgsConstructor
public class ManagerMemberController {
    private final ManagerService managerService;


    @GetMapping("")
    public MemberManagerResponses getMembersManager() {
        return managerService.getMembersForManagers();
    }

    @GetMapping("/{memberId}/info")
    public MemberInfoManagerResponse getMemberInfo(@PathVariable final long memberId) {
        return managerService.getMemberInfoForManager(memberId);
    }

    @GetMapping("/{memberId}/follows")
    public FollowManagerResponses getFollow(@PathVariable final long memberId) {
        return managerService.getMemberFollowInfo(memberId);
    }

    @GetMapping("/{memberId}/followings")
    public FollowingManagerResponses getFollowing(@PathVariable final long memberId) {
        return managerService.getMemberFollowingInfo(memberId);
    }

    @GetMapping("/{memberId}/teams")
    public TeamDataManagerResponses getTeam(@PathVariable final long memberId) {
        return managerService.getMemberTeamInfo(memberId);
    }

    @GetMapping("/{memberId}/showcases")
    public ShowcaseManagerResponses getShowcases(@PathVariable final long memberId) {
        return managerService.getMemberShowcaseInfo(memberId);
    }

    @GetMapping("/{memberId}/eventToasts")
    public EventToastDataManagerResponses getEventToasts(@PathVariable final long memberId) {
        return managerService.getMemberEventToastInfo(memberId);
    }
    @GetMapping("/{memberId}/giftToasts")
    public GiftToastDataManagerResponses getGiftToasts(@PathVariable final long memberId) {
        return managerService.getMemberGiftToastInfo(memberId);
    }
    @GetMapping("/{memberId}/iconGroups")
    public IconGroupManagerResponses getIconGroups(@PathVariable final long memberId) {
        return managerService.getMemberIconGroupInfo(memberId);
    }
    @GetMapping("/{memberId}/payments")
    public PaymentManagerResponses getPayment(@PathVariable final long memberId) {
        return managerService.getMemberPaymentManagerInfo(memberId);
    }
}
