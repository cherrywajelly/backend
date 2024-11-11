package com.timeToast.timeToast.controller.fcm;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/fcm")
@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PutMapping("/token")
    public void putFcmToken(@Login LoginMember loginMember, @RequestParam final String token) {
        fcmService.saveToken(loginMember.id(), token);
    }
}
