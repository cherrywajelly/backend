package com.timeToast.timeToast.controller.creator;

import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.member.member.MemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/creators")
@RestController
public class CreatorController {

    private final MemberService memberService;
    private final IconGroupAdminService iconGroupAdminService;

    public CreatorController(final MemberService memberService, final IconGroupAdminService iconGroupAdminService) {
        this.memberService = memberService;
        this.iconGroupAdminService = iconGroupAdminService;
    }

    @GetMapping("")
    public CreatorResponses getCreators() {
        return memberService.getCreators();
    }

    @GetMapping("/{creatorId}")
    public CreatorDetailResponse getCreatorByCreatorId(@PathVariable long creatorId) {
        return memberService.getCreatorByCreatorId(creatorId);
    }

    @GetMapping("/{creatorId}/iconGroups")
    public CreatorIconInfos getIconGroupsByCreator(@PathVariable long creatorId) {
        return iconGroupAdminService.getIconGroupsByCreator(creatorId);
    }
}
