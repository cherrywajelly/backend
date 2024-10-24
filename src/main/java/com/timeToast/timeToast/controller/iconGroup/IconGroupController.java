package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupController {
    private final IconGroupService iconGroupService;

    @PostMapping("/member/iconGroups/{icon_group_id}")
    public void buyIconGroup(@Login LoginMember loginMember, @PathVariable("icon_group_id") long iconGroupId) {
        iconGroupService.buyIconGroup(loginMember.id(), iconGroupId);
    }
}
