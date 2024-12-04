package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
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

    @PostMapping("/profile-image")
    public MemberInfoResponse saveProfileImage(@Login LoginMember loginMember, @RequestPart MultipartFile profileImage){
        return memberService.saveProfileImageByLogin(loginMember.id(), profileImage);
    }

    @PutMapping("")
    public MemberInfoResponse saveNickname(@Login LoginMember loginMember, @RequestParam("nickname") String nickname) {
        return memberService.postNickname(nickname, loginMember.id());
    }

    @GetMapping ("/nickname-validation")
    public Response isNicknameAvailable(@RequestParam("nickname") final String nickname) {
        return memberService.nicknameValidation(nickname);
    }

    @PostMapping("/refreshToken")
    public LoginResponse tokenRenewal(@RequestParam("refreshToken") final String refreshToken){
        return jwtService.tokenRenewal(refreshToken);
    }

    @GetMapping("/info")
    public MemberInfoResponse getMemberInfoByLogin(@Login final LoginMember loginMember){
        return memberService.getMemberInfo(loginMember.id());
    }

    @GetMapping("/{memberId}/info")
    public MemberInfoResponse getMemberInfo(@PathVariable long memberId){
        return memberService.getMemberInfo(memberId);
    }

    @GetMapping("")
    public MemberProfileResponse getMemberProfileInfoByLogin(@Login final LoginMember loginMember){
        return memberService.getMemberProfileByLogin(loginMember.id());
    }

    @GetMapping("/{memberId}")
    public MemberProfileResponse getProfileInfo(@Login final LoginMember loginMember, @PathVariable long memberId){
        return memberService.getMemberProfile(loginMember.id(), memberId);
    }

    @GetMapping("/premiums")
    public PremiumResponse getPremiumByLogin(@Login final LoginMember loginMember){
        return memberService.getMemberPremium(loginMember.id());
    }
}
