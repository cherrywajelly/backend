package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.MarketIconGroupResponses;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupResponses;
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

    @GetMapping("")
    public UserIconGroupResponses getUserIconGroups(@Login LoginMember loginMember, @RequestParam IconType iconType) {
        return iconService.getUserIconGroups(loginMember.id(), iconType);
    }

    @GetMapping("/market")
    public MarketIconGroupResponses getMarketIconGroups(@Login LoginMember loginMember, @RequestParam IconType iconType) {
        return iconService.getMarketIconGroups(loginMember.id(), iconType);
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
