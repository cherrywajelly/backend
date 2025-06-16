package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorProfileResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.AdminIconService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v2/iconGroups")
@Validated
@RestController
@RequiredArgsConstructor
public class FactoryIconGroupController {

    private final AdminIconService adminIconService;

    @PostMapping("")
    public Response postIconGroup(@Login LoginMember loginMember, @RequestPart("thumbnailIcon") MultipartFile thumbnailIcon, @RequestPart("files") List<MultipartFile> files, @RequestPart final IconGroupPostRequest iconGroupPostRequest) {
        return adminIconService.postIconGroup(thumbnailIcon, files, iconGroupPostRequest, loginMember.id());
    }

    @GetMapping("")
    public IconGroupCreatorResponses getIconGroup(@Login LoginMember loginMember) {
        return adminIconService.getIconGroupForCreator(loginMember.id());
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupCreatorDetailResponse getIconGroupDetail(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
        return adminIconService.getIconGroupDetailForCreator(loginMember.id(), iconGroupId);
    }

    @GetMapping("/salesInfo")
    public CreatorProfileResponse getCreatorProfile(@Login LoginMember loginMember) {
        return adminIconService.getIconGroupSaleInfos(loginMember.id());
    }
}
