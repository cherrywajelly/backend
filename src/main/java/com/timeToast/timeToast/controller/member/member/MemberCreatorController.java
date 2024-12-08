package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.member.member.CreatorService;
import com.timeToast.timeToast.service.member.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v2/members")
@RestController
@RequiredArgsConstructor
public class MemberCreatorController {

    private final CreatorService creatorService;
    private final MemberService memberService;

    @GetMapping("/nickname-validation")
    public Response isNicknameAvailable(@RequestParam("nickname") final String nickname) {
        return memberService.nicknameValidation(nickname);
    }

    @PostMapping("/creator-info")
    public Response saveCreatorInfo(@Login LoginMember loginMember, @RequestPart(value = "profile") final MultipartFile profile, @RequestPart CreatorRequest creatorRequest) {
        return memberService.saveCreatorInfo(loginMember.id(), profile, creatorRequest);
    }

    @GetMapping("")
    public CreatorProfileResponse getCreatorProfile(@Login LoginMember loginMember) {
        return creatorService.getCreatorProfile(loginMember.id());
    }

    @PutMapping("")
    public CreatorInfoResponse putCreatorInfo(@Login LoginMember loginMember, @RequestPart(value = "profile") final MultipartFile profile, @RequestPart final CreatorRequest creatorRequest) {
        return creatorService.putCreatorInfo(loginMember.id(), profile, creatorRequest);
    }
}
