package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.member.member.MemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/members")
@RestController
public class AppMemberController {

    private final MemberService memberService;

    public AppMemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public MemberProfileResponse getMemberProfileInfoByLogin(@Login final LoginMember loginMember){
        return memberService.getMemberProfile(loginMember.id());
    }

    @GetMapping("/{memberId}")
    public MemberProfileResponse getProfileInfo(@Login final LoginMember loginMember, @PathVariable long memberId){
        return memberService.getMemberProfile(loginMember.id(), memberId);
    }

    @GetMapping("/info")
    public MemberInfoResponse getMemberInfoByLogin(@Login final LoginMember loginMember){
        return memberService.getMemberInfo(loginMember.id());
    }

    @GetMapping("/{memberId}/info")
    public MemberInfoResponse getMemberInfo(@PathVariable long memberId){
        return memberService.getMemberInfo(memberId);
    }
}
