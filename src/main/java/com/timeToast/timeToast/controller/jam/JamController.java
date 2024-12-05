package com.timeToast.timeToast.controller.jam;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.jam.JamService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/jams")
@Validated
@RestController
@RequiredArgsConstructor
public class JamController {
    private final JamService jamService;

    @PostMapping("/{eventToastId}")
    public Response postJam(@Login LoginMember loginMember,
                        @PathVariable final long eventToastId,
                        @RequestPart("jamContents") final MultipartFile jamContents,
                        @RequestPart(value = "jamImages",required = false) final MultipartFile jamImages,
                        @RequestPart final JamRequest jamRequest) {
        return jamService.postJam(jamRequest, jamContents, jamImages, eventToastId, loginMember.id());
    }

    @GetMapping("/eventToast/{eventToastId}")
    public JamResponses getJams(@PathVariable final long eventToastId) {
        return jamService.getJams(eventToastId);
    }

    @GetMapping("/{jamId}")
    public JamDetailResponse getJam(@Login LoginMember loginMember, @PathVariable final long jamId) {
        return jamService.getJam(loginMember.id(), jamId);
    }

    @DeleteMapping("/{jamId}")
    public Response deleteJam(@Login LoginMember loginMember, @PathVariable final long jamId) {
        return jamService.deleteJam(loginMember.id(), jamId);
    }

}
