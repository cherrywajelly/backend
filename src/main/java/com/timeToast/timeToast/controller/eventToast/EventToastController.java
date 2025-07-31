package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastResponses;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastMyResponses;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastDetailResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.SuccessResponse;
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
    public SuccessResponse saveEventToast(@Login LoginMember loginMember, @RequestBody EventToastPostRequest eventToastPostRequest) {
        return eventToastService.saveEventToast(eventToastPostRequest, loginMember.id());
    }

    @GetMapping("/member")
    public EventToastMyResponses getMyEventToasts(@Login LoginMember loginMember) {
        return eventToastService.getMyEventToasts(loginMember.id());
    }

    //팔로우 하고 있는 타사용자의 이벤트 토스트 목록 조회
    @GetMapping("/follow/following")
    public EventToastResponses getFollowerEventToasts(@Login LoginMember loginMember) {
        return eventToastService.getEventToastsFromFollower(loginMember.id());
    }

    //타사용자 마이페이지의 이벤트 토스트 목록 조회
    @GetMapping("/member/{memberId}")
    public EventToastResponses getEventToasts(@Login LoginMember loginMember, @PathVariable final long memberId) {
        return eventToastService.getEventToastsOfFollower(loginMember.id(), memberId);
    }

    @GetMapping("/{eventToastId}")
    public EventToastDetailResponse getEventToastDetail(@Login LoginMember loginMember, @PathVariable final long eventToastId) {
        return eventToastService.getEventToastDetail(loginMember.id(), eventToastId);
    }

    @DeleteMapping("/{eventToastId}")
    public Response deleteEventToast(@Login LoginMember loginMember, @PathVariable final long eventToastId) {
        return eventToastService.deleteEventToast(loginMember.id(), eventToastId);
    }

}

