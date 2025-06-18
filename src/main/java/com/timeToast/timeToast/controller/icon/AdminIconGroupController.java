package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.service.icon.AdminIconService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/iconGroups")
@RestController
public class AdminIconGroupController {

    private final AdminIconService adminIconService;

    public AdminIconGroupController(AdminIconService adminIconService) {
        this.adminIconService = adminIconService;
    }

    @PostMapping("")
    public IconGroupInfoResponse saveIconState(@RequestBody IconGroupStateRequest iconGroupStateRequest) {
        return adminIconService.saveIconState(iconGroupStateRequest);
    }

    @GetMapping("")
    public IconGroupAdminResponses iconGroupList() {
        return adminIconService.getAllIconGroups();
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupDetailResponse iconGroupDetail(@PathVariable long iconGroupId) {
        return adminIconService.getIconGroupDetail(iconGroupId);
    }

    @GetMapping("/non-approval")
    public IconGroupInfoResponses iconGroupNonApproval() {
        return adminIconService.getIconGroupForNonApproval();
    }

}
