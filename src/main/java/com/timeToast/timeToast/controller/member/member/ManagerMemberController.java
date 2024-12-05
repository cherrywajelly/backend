package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
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
    private final MemberService memberService;

    @GetMapping("")
    public MemberManagerResponses getMembersManager() {
        return memberService.getMembersForManagers();
    }

    @GetMapping("/{memberId}")
    public MemberInfoManagerResponse getMemberInfoManager(@PathVariable final long memberId) {
        return memberService.getMemberInfoForManager(memberId);
    }
}
