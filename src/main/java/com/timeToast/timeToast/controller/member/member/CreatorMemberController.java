package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.member.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/members")
@RestController
@RequiredArgsConstructor
public class CreatorMemberController {
    private final MemberService memberService;

    @GetMapping("/nickname-validation")
    public Response isNicknameAvailable(@RequestParam("nickname") final String nickname) {
        return memberService.nicknameValidation(nickname);
    }

    @PostMapping("/creator-info")
    public Response saveCreatorInfo(@Login LoginMember loginMember, @RequestBody CreatorRequest creatorRequest) {
        return memberService.saveCreatorInfo(loginMember.id(), creatorRequest);
    }
}
