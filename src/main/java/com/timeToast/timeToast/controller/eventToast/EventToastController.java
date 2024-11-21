package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.*;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/eventToasts")
@Validated
@RestController
@RequiredArgsConstructor
public class EventToastController {

    private final EventToastService eventToastService;

    @PostMapping("")
    public Response postEventToast(@Login LoginMember loginMember, @RequestBody EventToastPostRequest eventToastPostRequest) {
        return eventToastService.postEventToast(eventToastPostRequest, loginMember.id());
    }

    @GetMapping("/member")
    public EventToastOwnResponses getOwnEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getOwnEventToastList(loginMember.id());
    }

    @GetMapping("/member/{memberId}")
    public EventToastMemberResponses getMemberEventToastList(@Login LoginMember loginMember, @PathVariable final long memberId) {
        return eventToastService.getMemberEventToastList(loginMember.id(), memberId);
    }

    @GetMapping("/follow/following")
    public EventToastFriendResponses getFriendEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getEventToasts(loginMember.id());
    }

    @GetMapping("/{eventToastId}")
    public EventToastResponse getEventToast(@Login LoginMember loginMember, @PathVariable final long eventToastId) {
        return eventToastService.getEventToast(loginMember.id(), eventToastId);
    }

    @DeleteMapping("/{eventToastId}")
    public Response deleteEventToast(@Login LoginMember loginMember, @PathVariable final long eventToastId) {
        return eventToastService.deleteEventToast(loginMember.id(), eventToastId);
    }

}
