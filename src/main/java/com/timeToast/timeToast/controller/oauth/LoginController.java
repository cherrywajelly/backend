package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.service.oauth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/api/v1/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

//     for test
     @GetMapping("")
     public RedirectView loadUrl () {
        return new RedirectView(loginService.loadToKakaoLogin());
     }

    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return loginService.getAccessToken("kakao", code);
    }

}
