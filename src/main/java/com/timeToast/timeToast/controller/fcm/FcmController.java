package com.timeToast.timeToast.controller.fcm;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping("/api/v1/fcm")
@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PutMapping("")
    public Response putFcmToken(@Login LoginMember loginMember, @RequestParam final String token) {
        return fcmService.saveToken(loginMember.id(), token);
    }

    @PostMapping("/send")
    public Response test(@Login LoginMember loginMember, @RequestBody FcmResponse fcmResponse) {
        return fcmService.sendMessageTo(loginMember.id(), fcmResponse);
    }

    @GetMapping("")
    public List<FcmResponses> getFcmMessages(@Login LoginMember loginMember) {
        return fcmService.getFcmResponses(loginMember.id());
    }


    @GetMapping("/opened/{fcmId}")
    public Response putIsOpened(@Login LoginMember loginMember, @PathVariable final long fcmId) {
        return fcmService.putIsOpened(loginMember.id(), fcmId);
    }
}
