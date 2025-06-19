package com.timeToast.timeToast.controller.icon;

import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.IconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.IconGroupDetailResponses;
import com.timeToast.timeToast.dto.icon.response.IconGroupSummaryInfo;
import com.timeToast.timeToast.dto.icon.response.IconGroupSummaryInfos;
import com.timeToast.timeToast.service.icon.AdminIconService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminIconGroupController {

    private final AdminIconService adminIconService;

    public AdminIconGroupController(AdminIconService adminIconService) {
        this.adminIconService = adminIconService;
    }

    @PostMapping("/api/v3/iconGroups")
    public IconGroupSummaryInfo saveIconState(@RequestBody IconGroupStateRequest iconGroupStateRequest) {
        return adminIconService.saveIconState(iconGroupStateRequest);
    }

    @GetMapping("/api/v3/iconGroups")
    public IconGroupSummaryInfos iconGroupList() {
        return adminIconService.getAllIconGroups();
    }

    @GetMapping("/api/v3/iconGroups/{iconGroupId}")
    public IconGroupDetail iconGroupDetail(@PathVariable long iconGroupId) {
        return adminIconService.getIconGroupDetail(iconGroupId);
    }

    @GetMapping("/api/v3/iconGroups/non-approval")
    public IconGroupSummaryInfos iconGroupNonApproval() {
        return adminIconService.getIconGroupForNonApproval();
    }

    @GetMapping("/api/v3/iconGroups/creators/{creatorId}")
    public CreatorIconGroupResponse getIconGroupsByCreator(@PathVariable long creatorId) {
        return adminIconService.getIconGroupsByCreator(creatorId);
    }

    @GetMapping("/api/v3/iconGroups/members/{memberId}")
    public IconGroupDetailResponses getIconGroups(@PathVariable final long memberId) {
        return adminIconService.getMemberIconGroupInfo(memberId);
    }

}
