package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.service.oauth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpHeaders;

@RequestMapping("/api/v1/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

//     for test
     @GetMapping("")
     public RedirectView loadUrl () {
        return new RedirectView(loginService.loadToGoogleLogin());
     }



    @PostMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return loginService.getAccessToken(LoginType.KAKAO, code);
    }

    @PostMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return loginService.getAccessToken(LoginType.GOOGLE, code);
    }

}
