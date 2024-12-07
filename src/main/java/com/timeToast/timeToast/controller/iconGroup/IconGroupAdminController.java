package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/iconGroups")
@RestController
public class IconGroupAdminController {

    private final IconGroupAdminService iconGroupAdminService;

    public IconGroupAdminController(IconGroupAdminService iconGroupAdminService) {
        this.iconGroupAdminService = iconGroupAdminService;
    }

    @PostMapping("")
    public IconGroupInfoResponse saveIconState(@RequestBody IconGroupStateRequest iconGroupStateRequest) {
        return iconGroupAdminService.saveIconState(iconGroupStateRequest);
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupDetailResponse iconGroupDetail(@PathVariable long iconGroupId) {
        return iconGroupAdminService.getIconGroupDetail(iconGroupId);
    }

    @GetMapping("")
    public IconGroupInfoResponses iconGroupList() {
        return iconGroupAdminService.getAllIconGroups();
    }

    @GetMapping("/non-approval")
    public IconGroupInfoResponses iconGroupNonApproval() {
        return iconGroupAdminService.getIconGroupForNonApproval();
    }

    @GetMapping("/summary")
    public IconGroupSummaries iconGroupSummary() {
        return iconGroupAdminService.iconGroupSummary();
    }

    @GetMapping(value = "/summary", params = {"year", "month"})
    public IconGroupSummaries iconGroupSummary(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month) {
        return iconGroupAdminService.iconGroupSummaryByYearMonth(year, month);
    }

    @GetMapping("/monthly-revenue")
    public IconGroupMonthlyRevenues iconGroupMonthlyRevenue(@RequestParam(value = "year") int year) {
        return iconGroupAdminService.iconGroupMonthlyRevenue(year);
    }

}
