package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.jwt.JwtService;
import com.timeToast.timeToast.service.member.member.MemberService;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("/profile-image")
    public MemberInfoResponse saveProfileImage(@Login LoginMember loginMember, @RequestPart MultipartFile profileImage){
        return memberService.saveProfileImageByLogin(loginMember.id(), profileImage);
    }

    @PutMapping("")
    public void saveNickname(@Login LoginMember loginMember, @RequestParam("nickname") String nickname) {
        memberService.postNickname(nickname, loginMember.id());
    }

    @GetMapping ("/nickname-validation")
    public void isNicknameAvailable(@RequestParam("nickname") String nickname) {
        memberService.nicknameValidation(nickname);
    }

    @PostMapping("/refreshToken")
    public LoginResponse tokenRenewal(@RequestParam("refreshToken") String refreshToken){
        return jwtService.tokenRenewal(refreshToken);
    }

    @GetMapping("/info")
    public MemberInfoResponse getMemberInfoByLogin(@Login LoginMember loginMember){
        return memberService.getMemberInfo(loginMember.id());
    }

    @GetMapping("/{memberId}/info")
    public MemberInfoResponse getMemberInfo(@PathVariable long memberId){
        return memberService.getMemberInfo(memberId);
    }

    @GetMapping("")
    public MemberProfileResponse getMemberProfileInfoByLogin(@Login LoginMember loginMember){
        return memberService.getMemberProfile(loginMember.id());
    }

    @GetMapping("/{memberId}")
    public MemberProfileResponse getProfileInfo(@PathVariable long memberId){
        return memberService.getMemberProfile(memberId);
    }
}
