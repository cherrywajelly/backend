package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.jwt.JwtService;
import com.timeToast.timeToast.service.member.member.MemberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    public MemberController(final MemberService memberService, final JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @PostMapping("/refreshToken")
    public LoginResponse tokenRenewal(@RequestParam("refreshToken") final String refreshToken){
        return jwtService.tokenRenewal(refreshToken);
    }

    @PostMapping("/profile-image")
    public MemberInfoResponse saveProfileImage(@Login LoginMember loginMember, @RequestPart MultipartFile profileImage){
        return memberService.saveProfileImage(loginMember.id(), profileImage);
    }

    @GetMapping("/nickname-validation")
    public Response isNicknameAvailable(@RequestParam("nickname") final String nickname) {
        return memberService.nicknameValidation(nickname);
    }

    @PutMapping("")
    public MemberInfoResponse saveNickname(@Login LoginMember loginMember, @RequestParam("nickname") String nickname) {
        return memberService.saveNickname(nickname, loginMember.id());
    }

}
