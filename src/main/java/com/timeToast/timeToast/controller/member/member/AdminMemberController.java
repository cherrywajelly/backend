package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.member.member.AdminMemberService;
import com.timeToast.timeToast.service.member.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final IconGroupAdminService iconGroupAdminService;



    @GetMapping("/api/v3/members")
    public MemberInfoResponses getMembersManager() {
        return adminMemberService.getMembersForManagers();
    }

    @GetMapping("/api/v3/members/count")
    public MemberSummaryResponse getMembersCountManager() {
        return adminMemberService.getMembersCountForManagers();
    }

    @GetMapping("/api/v3/members/{memberId}/info")
    public MemberInfoResponse getMemberInfo(@PathVariable final long memberId) {
        return memberService.getMemberInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/follows")
    public FollowManagerResponses getFollow(@PathVariable final long memberId) {
        return adminMemberService.getMemberFollowInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/followings")
    public FollowingManagerResponses getFollowing(@PathVariable final long memberId) {
        return adminMemberService.getMemberFollowingInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/teams")
    public TeamDataManagerResponses getTeam(@PathVariable final long memberId) {
        return adminMemberService.getMemberTeamInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/showcases")
    public ShowcaseManagerResponses getShowcases(@PathVariable final long memberId) {
        return adminMemberService.getMemberShowcaseInfo(memberId);
    }

    @GetMapping("/api/v3/members/{memberId}/eventToasts")
    public EventToastDataManagerResponses getEventToasts(@PathVariable final long memberId) {
        return adminMemberService.getMemberEventToastInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/giftToasts")
    public GiftToastDataManagerResponses getGiftToasts(@PathVariable final long memberId) {
        return adminMemberService.getMemberGiftToastInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/iconGroups")
    public IconGroupManagerResponses getIconGroups(@PathVariable final long memberId) {
        return adminMemberService.getMemberIconGroupInfo(memberId);
    }
    @GetMapping("/api/v3/members/{memberId}/payments")
    public PaymentManagerResponses getPayment(@PathVariable final long memberId) {
        return adminMemberService.getMemberPaymentManagerInfo(memberId);
    }
    @GetMapping("/api/v3/creators")
    public CreatorResponses getCreators() {
        return memberService.getCreators();
    }

    @GetMapping("/api/v3/creators/{creatorId}")
    public CreatorInfoResponse getCreatorByCreatorId(@PathVariable long creatorId) {
        return memberService.getCreatorInfo(creatorId);
    }

    @GetMapping("/api/v3/creators/{creatorId}/iconGroups")
    public CreatorIconInfos getIconGroupsByCreator(@PathVariable long creatorId) {
        return iconGroupAdminService.getIconGroupsByCreator(creatorId);
    }

    @PostMapping("/api/v4/members/{memberId}/staffs")
    public MemberInfoResponse saveToStaff(@PathVariable final long memberId){
        return memberService.saveToStaff(memberId);
    }

    @PostMapping("/api/v4/members/{memberId}/creators")
    public MemberInfoResponse saveToCreators(@PathVariable final long memberId){
        return memberService.saveToCreators(memberId);
    }


    @PostMapping("/api/v4/members/{memberId}/users")
    public MemberInfoResponse saveToUser(@PathVariable final long memberId){
        return memberService.saveToUser(memberId);
    }
}
