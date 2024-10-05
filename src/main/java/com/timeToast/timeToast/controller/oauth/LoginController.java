package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.service.oauth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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



    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return loginService.getAccessToken(LoginType.KAKAO, code);
    }

    @GetMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return loginService.getAccessToken(LoginType.GOOGLE, code);
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> postNickname(@RequestParam("nickname") String nickname) {
         return loginService.postNickname(nickname);
    }
}
