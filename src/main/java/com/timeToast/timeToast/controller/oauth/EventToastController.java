package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/eventToast")
@RestController
@RequiredArgsConstructor
public class EventToastController {

    private final EventToastService eventToastService;
}
