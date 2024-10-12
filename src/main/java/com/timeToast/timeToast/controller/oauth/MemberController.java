package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.service.oauth.LoginService;
import com.timeToast.timeToast.service.oauth.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 닉네임 등록
    @PostMapping("")
    public ResponseEntity<String> postNickname(@RequestParam("nickname") String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userId = (long)authentication.getPrincipal();
        return memberService.postNickname(nickname, userId);
    }
}
