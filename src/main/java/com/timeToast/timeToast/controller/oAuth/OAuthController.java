package com.timeToast.timeToast.controller.oAuth;

import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.service.oAuth.OAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/login")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(final OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return oAuthService.getKakaoAccessToken(code);
    }

    @GetMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return oAuthService.getGoogleAccessToken(code);
    }

}
