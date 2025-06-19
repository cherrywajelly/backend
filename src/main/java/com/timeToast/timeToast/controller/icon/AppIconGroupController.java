package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.IconGroupInfoResponses;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetailResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class AppIconGroupController {
    private final IconService iconService;

    @GetMapping("/members/toasts")
    public UserIconGroupDetailResponses getToastIconGroupsByMember(@Login LoginMember loginMember) {
        return iconService.getToastIconGroupsByUser(loginMember.id());
    }

    @GetMapping("/members/jams")
    public UserIconGroupDetailResponses getJamIconGroupsByMember(@Login LoginMember loginMember) {
        return iconService.getJamIconGroupsByUser(loginMember.id());
    }

    @GetMapping("/toasts")
    public IconGroupInfoResponses getAllToastsIconGroups(@Login LoginMember loginMember) {
        return iconService.getAllToastsIconGroups(loginMember.id());
    }

    @GetMapping("/jams")
    public IconGroupInfoResponses getAllJamsIconGroups(@Login LoginMember loginMember) {
        return iconService.getAllJamsIconGroups(loginMember.id());
    }

    @GetMapping("/{iconGroupId}")
    public UserIconGroupDetail getIconGroupDetail(@Login LoginMember loginMember, final @PathVariable long iconGroupId) {
        return iconService.getIconGroupDetail(loginMember.id(), iconGroupId);
    }

    @DeleteMapping("/{iconGroupId}")
    public Response deleteIconGroup(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        return iconService.deleteIconGroup(loginMember.id(), iconGroupId);
    }

}
