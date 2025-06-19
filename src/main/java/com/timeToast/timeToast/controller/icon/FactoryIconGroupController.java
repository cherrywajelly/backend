package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.response.IconGroupOverview;
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
    public Response postIconGroup(@Login LoginMember loginMember, @RequestPart("thumbnailIcon") MultipartFile thumbnailIcon,
                                  @RequestPart("files") List<MultipartFile> files,
                                  @RequestPart final IconGroupPostRequest iconGroupPostRequest) {
        return adminIconService.postIconGroup(thumbnailIcon, files, iconGroupPostRequest, loginMember.id());
    }

    @GetMapping("")
    public CreatorIconGroupResponse getIconGroupOverviews(@Login LoginMember loginMember) {
        return adminIconService.getIconGroupsByCreator(loginMember.id());
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupOverview getIconGroupOverview(@Login LoginMember loginMember,
                                                @PathVariable("iconGroupId") final long iconGroupId) {
        return adminIconService.getIconGroupOverview(loginMember.id(), iconGroupId);
    }

}
