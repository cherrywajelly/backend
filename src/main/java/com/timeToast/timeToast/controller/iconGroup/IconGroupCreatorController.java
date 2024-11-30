package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.icon.IconService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v2/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class IconGroupCreatorController {

    private final IconGroupAdminService iconGroupAdminService;
    private final IconService iconService;

    @PostMapping("")
    public Response postIconGroup(@Login LoginMember loginMember, @RequestBody IconGroupPostRequest iconGroupPostRequest) {
        return iconGroupAdminService.postIconGroup(iconGroupPostRequest, loginMember.id());
    }

    @PostMapping("/images/{iconGroupId}")
    public Response postIconGroupImages(@Login LoginMember loginMember, @RequestParam("files") List<MultipartFile> files, @PathVariable("iconGroupId") final long iconGroupId) {
        return iconService.postIconSet(files, iconGroupId);
    }

    @GetMapping("")
    public IconGroupCreatorResponses getIconGroup(@Login LoginMember loginMember) {
        return iconGroupAdminService.getIconGroupForCreator(loginMember.id());
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupCreatorDetailResponse getIconGroupDetail(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        return iconGroupAdminService.getIconGroupDetailForCreator(loginMember.id(), iconGroupId);
    }
}
