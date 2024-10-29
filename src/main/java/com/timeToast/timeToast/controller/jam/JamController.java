package com.timeToast.timeToast.controller.jam;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.jam.JamService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/jams")
@Validated
@RestController
@RequiredArgsConstructor
public class JamController {
    private final JamService jamService;

    @PostMapping("/{eventToastId}")
    public void postJam(@Login LoginMember loginMember, @RequestBody JamRequest jamRequest,
                        @PathVariable final long eventToastId) {
        jamService.postJam(jamRequest, eventToastId, loginMember.id());
    }
}
