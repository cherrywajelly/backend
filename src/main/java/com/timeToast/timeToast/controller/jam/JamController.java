package com.timeToast.timeToast.controller.jam;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
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
    private final ToastPieceService toastPieceService;

    @PostMapping("/{eventToastId}")
    public void postJam(@Login LoginMember loginMember,
                        @PathVariable final long eventToastId,
                        @RequestPart("jamContents") final MultipartFile jamContents,
                        @RequestPart("jamImages") final MultipartFile jamImages,
                        @RequestPart final JamRequest jamRequest) {
        jamService.postJam(jamRequest, jamContents, jamImages, eventToastId, loginMember.id());
    }


    @GetMapping("/eventToast/{eventToastId}")
    public List<JamResponses> getJam(@PathVariable final long eventToastId) {
        return jamService.getJams(eventToastId);
    }

    @GetMapping("/{jamId}")
    public JamResponse getJam(@Login LoginMember loginMember, @PathVariable final long jamId) {
        return jamService.getJam(loginMember.id(), jamId);
    }

}
