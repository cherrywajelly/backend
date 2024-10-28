package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/eventToasts")
@Validated
@RestController
@RequiredArgsConstructor
public class EventToastController {

    private final EventToastService eventToastService;

    @PostMapping("")
    public void postEventToast(@Login LoginMember loginMember, @RequestBody EventToastPostRequest eventToastPostRequest) {
        eventToastService.postEventToast(eventToastPostRequest, loginMember.id());
    }

    @GetMapping("/follow/following")
    public List<EventToastResponses> getEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getEventToasts(loginMember.id());
    }

    @GetMapping("/member")
    public List<EventToastOwnResponse> getOwnEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getOwnEventToastList(loginMember.id());
    }

    @GetMapping("/member/{memberId}")
    public List<EventToastFriendResponse> getFriendEventToastList(@Login LoginMember loginMember,
                                                                  @PathVariable final long memberId) {
        return eventToastService.getFriendEventToastList(loginMember.id(), memberId);
    }

    @GetMapping("/{eventToastId}")
    public EventToastResponse getEventToast(@Login LoginMember loginMember,
                                            @PathVariable final long eventToastId) {
        return eventToastService.getEventToast(loginMember.id(), eventToastId);
    }
}
