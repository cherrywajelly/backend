package com.timeToast.timeToast.controller.member.oauth;

import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.service.member.oauth.OAuthService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/login")
@RestController
public class OAuthCreatorController {

    private final OAuthService oAuthService;
    public OAuthCreatorController(final OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return oAuthService.kakaoLoginCreator(code);
    }

    @GetMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return oAuthService.googleLoginCreator(code);
    }
}
