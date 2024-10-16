package com.timeToast.timeToast.controller;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon_group.IconGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupController {

    private final IconGroupService iconGroupService;

    // TODO 관리자 권한 인증

    @PostMapping("/v2/iconGroup")
    public void postIconGroup(@Login LoginMember loginMember, @RequestBody IconGroupPostRequest iconGroupPostRequest) {
        iconGroupService.postIconGroup(iconGroupPostRequest, loginMember.id());
    }

}
