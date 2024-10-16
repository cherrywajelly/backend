package com.timeToast.timeToast.controller;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/eventToast")
@RestController
@RequiredArgsConstructor
public class EventToastController {

    private final EventToastService eventToastService;

    @PostMapping("")
    public void postEventToast(@Login LoginMember loginMember, @RequestBody EventToastPostRequest eventToastPostRequest) {
        eventToastService.postEventToast(eventToastPostRequest, loginMember.id());
    }

    @GetMapping("")
    public List<EventToastResponse> getEventToast(@Login LoginMember loginMember) {
        return eventToastService.getEventToastList(loginMember.id());
    }
}
