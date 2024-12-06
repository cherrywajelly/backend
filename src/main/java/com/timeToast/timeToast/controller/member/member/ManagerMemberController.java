package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
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

    @GetMapping("/{memberId}/follow")
    public FollowManagerResponses getFollow(@PathVariable final long memberId) {
        return managerService.getMemberFollowInfo(memberId);
    }

}
