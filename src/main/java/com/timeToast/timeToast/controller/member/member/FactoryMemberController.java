package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.dto.member.member.response.CreatorInfoResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.member.member.MemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/members")
@RestController
public class FactoryMemberController {

    private final MemberService memberService;

    public FactoryMemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public CreatorInfoResponse saveCreatorInfo(@Login LoginMember loginMember, @RequestBody CreatorAccount creatorAccount) {
        return memberService.saveCreatorInfo(loginMember.id(), creatorAccount);
    }

}
