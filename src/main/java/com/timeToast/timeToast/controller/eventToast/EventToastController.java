package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
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


    // 이벤트 토스트 등록
    @PostMapping("")
    public Response postEventToast(@Login LoginMember loginMember, @RequestBody EventToastPostRequest eventToastPostRequest) {
        return eventToastService.postEventToast(eventToastPostRequest, loginMember.id());
    }


    // 유저 마이페이지 이벤트 토스트 목록 조회
    @GetMapping("/member")
    public List<EventToastOwnResponse> getOwnEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getOwnEventToastList(loginMember.id());
    }


    // 특정 사용자의 이벤트 토스트 목록 조회
    @GetMapping("/member/{memberId}")
    public List<EventToastFriendResponse> getFriendEventToastList(@Login LoginMember loginMember,
                                                                  @PathVariable final long memberId) {
        return eventToastService.getFriendEventToastList(loginMember.id(), memberId);
    }


    // 사용자가 팔로우 하고 있는 타사용자의 이벤트 토스트 목록 조회
    @GetMapping("/follow/following")
    public List<EventToastResponses> getEventToastList(@Login LoginMember loginMember) {
        return eventToastService.getEventToasts(loginMember.id());
    }

    // 사용자 이벤트 토스트 상세 조회
    @GetMapping("/{eventToastId}")
    public EventToastResponse getEventToast(@Login LoginMember loginMember,
                                            @PathVariable final long eventToastId) {
        return eventToastService.getEventToast(loginMember.id(), eventToastId);
    }


    // 이벤트 토스트 삭제
    @DeleteMapping("/{eventToastId}")
    public Response deleteEventToast(@Login LoginMember loginMember,
                                 @PathVariable final long eventToastId) {
        return eventToastService.deleteEventToast(loginMember.id(), eventToastId);
    }

}
