package com.timeToast.timeToast.controller.member.member;

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
import com.timeToast.timeToast.service.member.member.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberAdminController {
    private final ManagerService managerService;

    @PostMapping("/api/v4/members/{memberId}/staffs")
    public MemberAdminResponse saveToStaff(@PathVariable final long memberId){
        return managerService.saveToStaff(memberId);
    }

    @PostMapping("/api/v4/members/{memberId}/creators")
    public MemberAdminResponse saveToCreators(@PathVariable final long memberId){
        return managerService.saveToCreators(memberId);
    }


    @PostMapping("/api/v4/members/{memberId}/users")
    public MemberAdminResponse saveToUser(@PathVariable final long memberId){
        return managerService.saveToUser(memberId);
    }

    @GetMapping("/api/v3/members")
    public MemberManagerResponses getMembersManager() {
        return managerService.getMembersForManagers();
    }

    @GetMapping("/api/v3/members/count")
    public MemberSummaryResponse getMembersCountManager() {
        return managerService.getMembersCountForManagers();
    }

    @GetMapping("/api/v3/members/{memberId}/info")
    public MemberInfoManagerResponse getMemberInfo(@PathVariable final long memberId) {
        return managerService.getMemberInfoForManager(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/follows")
    public FollowManagerResponses getFollow(@PathVariable final long memberId) {
        return managerService.getMemberFollowInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/followings")
    public FollowingManagerResponses getFollowing(@PathVariable final long memberId) {
        return managerService.getMemberFollowingInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/teams")
    public TeamDataManagerResponses getTeam(@PathVariable final long memberId) {
        return managerService.getMemberTeamInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/showcases")
    public ShowcaseManagerResponses getShowcases(@PathVariable final long memberId) {
        return managerService.getMemberShowcaseInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/eventToasts")
    public EventToastDataManagerResponses getEventToasts(@PathVariable final long memberId) {
        return managerService.getMemberEventToastInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/giftToasts")
    public GiftToastDataManagerResponses getGiftToasts(@PathVariable final long memberId) {
        return managerService.getMemberGiftToastInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/iconGroups")
    public IconGroupManagerResponses getIconGroups(@PathVariable final long memberId) {
        return managerService.getMemberIconGroupInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/payments")
    public PaymentManagerResponses getPayment(@PathVariable final long memberId) {
        return managerService.getMemberPaymentManagerInfo(memberId);
    }

}
