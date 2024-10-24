package com.timeToast.timeToast.controller.member.oauth;

import com.timeToast.timeToast.dto.member.member.LoginResponse;
import com.timeToast.timeToast.service.oAuth.OAuthService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/login")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(final OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

//     for test
//     @GetMapping("")
//     public RedirectView loadUrl () {
//        return new RedirectView(oAuthService.loadToLogin());
//     }

//     @GetMapping("/test")
//     public RedirectView loadKakaoUrl () {
//         return new RedirectView(oAuthService.loadToKakaoLogin());
//     }


    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return oAuthService.getKakaoAccessToken(code);
    }

    @GetMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return oAuthService.getGoogleAccessToken(code);
    }

}
