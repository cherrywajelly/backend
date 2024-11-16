package com.timeToast.timeToast.controller.member.oauth;

import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.service.member.oauth.OAuthService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/login")
@RestController
public class OAuthAdminController {
    private final OAuthService oAuthService;
    public OAuthAdminController(final OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/kakao")
    public LoginResponse loginWithKakao(@RequestParam("code") String code) {
        return oAuthService.kakaoLoginAdmin(code);
    }

    @GetMapping("/google")
    public LoginResponse loginWithGoogle(@RequestParam("code") String code) {
        return oAuthService.googleLoginAdmin(code);
    }
}
