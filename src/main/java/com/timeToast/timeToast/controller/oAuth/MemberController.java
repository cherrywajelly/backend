package com.timeToast.timeToast.controller.oAuth;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.jwt.JwtService;
import com.timeToast.timeToast.service.oAuth.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    // 닉네임 등록
    @PostMapping("")
    public ResponseEntity<String> postNickname(@Login LoginMember loginMember, @RequestParam("nickname") String nickname) {

        return memberService.postNickname(nickname, loginMember.id());
    }

    @PostMapping("/refreshToken")
    public LoginResponse tokenRenewal(@RequestParam("refreshToken") String refreshToken){
        return jwtService.tokenRenewal(refreshToken);
    }
}
