package com.timeToast.timeToast.controller.oauth;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/eventToast")
@RestController
@RequiredArgsConstructor
public class EventToastController {

    private final EventToastService eventToastService;

    @PostMapping("")
    public void postEventToast(@Login LoginMember loginMember, @RequestBody @Valid EventToastPostRequest eventToastPostRequest) {
        eventToastService.postEventToast(eventToastPostRequest, loginMember.id());
    }

}
