package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
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

    @GetMapping("/members/toasts")
    public List<IconGroupResponses> getToastIconGroupsByMember(@Login LoginMember loginMember) {
        return iconGroupService.getToastIconGroups(loginMember.id());
    }

    @GetMapping("/members/jams")
    public List<IconGroupResponses> getJamIconGroupsByMember(@Login LoginMember loginMember) {
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
    public IconGroupDetailResponse getIconGroupDetail(final @PathVariable long iconGroupId) {
        return iconGroupService.getIconGroupDetail(iconGroupId);
    }

    @DeleteMapping("/{iconGroupId}")
    public void deleteIconGroup(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        iconGroupService.deleteIconGroup(loginMember.id(), iconGroupId);
    }

}
