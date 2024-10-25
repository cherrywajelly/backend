package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon.request.IconPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.exception.ForbiddenException;
import com.timeToast.timeToast.service.icon.icon.IconService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@RequestMapping("/api/v2/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupAdminController {

    private final IconGroupAdminService iconGroupAdminService;
    private final IconService iconService;

    @PostMapping("")
    public void postIconGroup(@Login LoginMember loginMember, @RequestBody IconGroupPostRequest iconGroupPostRequest) {
        if (loginMember.role().equals(MemberRole.CREATOR)) {
            iconGroupAdminService.postIconGroup(iconGroupPostRequest, loginMember.id());
        } else {
            throw new ForbiddenException(ROLE_FORBIDDEN.getMessage());
        }
        iconGroupAdminService.postIconGroup(iconGroupPostRequest, loginMember.id());
    }

    // TODO s3 이미지 처리
    @PostMapping("/images/{icon_group_id}")
    public void postIconGroupImages(@Login LoginMember loginMember, @PathVariable("icon_group_id") long iconGroupId, @RequestBody List<IconPostRequest> images) {
        if (loginMember.role().equals(MemberRole.CREATOR)) {
            iconService.postIconSet(images, iconGroupId);
        } else {
            throw new ForbiddenException(ROLE_FORBIDDEN.getMessage());
        }
        iconService.postIconSet(images, iconGroupId);
    }

}