package com.timeToast.timeToast.controller.member.member;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.member.member.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v2/members")
@RestController
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping("")
    public CreatorProfileResponse getCreatorProfile(@Login LoginMember loginMember) {
        return creatorService.getCreatorProfile(loginMember.id());
    }

    @PutMapping("")
    public CreatorInfoResponse putCreatorInfo(@Login LoginMember loginMember, MultipartFile file, @RequestPart final CreatorRequest creatorRequest) {
        return creatorService.putCreatorInfo(loginMember.id(), file, creatorRequest);
    }
}
