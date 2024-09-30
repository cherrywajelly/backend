package com.timeToast.timeToast.controller;

import com.timeToast.timeToast.service.LoginService;
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

    @GetMapping("/kakao")
    public ResponseEntity<?> loginWithKakao(@RequestParam("code") String code) {
        return ResponseEntity.ok().body(loginService.getAccessToken("kakao", code));
    }

}
