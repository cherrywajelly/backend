package com.timeToast.timeToast.controller;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.icon.request.IconPostRequest;
import com.timeToast.timeToast.dto.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon.IconService;
import com.timeToast.timeToast.service.icon_group.IconGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupController {

    private final IconGroupService iconGroupService;
    private final IconService iconService;

    // TODO 관리자 권한 인증

    @PostMapping("/v2/iconGroup")
    public void postIconGroup(@Login LoginMember loginMember, @RequestBody IconGroupPostRequest iconGroupPostRequest) {
        iconGroupService.postIconGroup(iconGroupPostRequest, loginMember.id());
    }

    // TODO s3 이미지 처리
    @PostMapping("/v2/iconGroup/image/{icon_group_id}")
    public void postIconGroupImages(@PathVariable("icon_group_id") long iconGroupId, @RequestBody List<IconPostRequest> images) {
        iconService.postIconSet(images, iconGroupId);
    }

}
