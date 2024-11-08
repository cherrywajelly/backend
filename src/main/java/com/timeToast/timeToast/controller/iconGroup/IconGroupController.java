package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupController {
    private final IconGroupService iconGroupService;

    @PostMapping("/members/{iconGroupId}")
    public void buyIconGroup(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        iconGroupService.buyIconGroup(loginMember.id(), iconGroupId);
    }

    @GetMapping("")
    public List<IconGroupResponses> getIconGroups(@Login LoginMember loginMember) {
        return iconGroupService.getIconGroups(loginMember.id());
    }

}
