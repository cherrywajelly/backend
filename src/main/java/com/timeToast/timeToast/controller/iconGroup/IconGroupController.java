package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupController {
    private final IconGroupService iconGroupService;

    @GetMapping("/members/toasts")
    public IconGroupResponses getToastIconGroupsByMember(@Login LoginMember loginMember) {
        return iconGroupService.getToastIconGroups(loginMember.id());
    }

    @GetMapping("/members/jams")
    public IconGroupResponses getJamIconGroupsByMember(@Login LoginMember loginMember) {
        return iconGroupService.getJamIconGroups(loginMember.id());
    }

    @GetMapping("/toasts")
    public IconGroupMarketResponses getAllToastsIconGroups(@Login LoginMember loginMember) {
        return iconGroupService.getAllToastsIconGroups(loginMember.id());
    }

    @GetMapping("/jams")
    public IconGroupMarketResponses getAllJamsIconGroups(@Login LoginMember loginMember) {
        return iconGroupService.getAllJamsIconGroups(loginMember.id());
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupMarketDetailResponse getIconGroupDetail(final @Login LoginMember loginMember, final @PathVariable long iconGroupId) {
        return iconGroupService.getIconGroupDetail(loginMember.id(), iconGroupId);
    }

    @DeleteMapping("/{iconGroupId}")
    public Response deleteIconGroup(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        return iconGroupService.deleteIconGroup(loginMember.id(), iconGroupId);
    }

}
