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

    @GetMapping("/{iconGroupId}")
    public IconGroupDetailResponse iconGroupDetail(@PathVariable long iconGroupId) {
        return adminIconService.getIconGroupDetail(iconGroupId);
    }

    @GetMapping("")
    public IconGroupAdminResponses iconGroupList() {
        return adminIconService.getAllIconGroups();
    }

    @GetMapping("/non-approval")
    public IconGroupInfoResponses iconGroupNonApproval() {
        return adminIconService.getIconGroupForNonApproval();
    }

    @GetMapping("/summary")
    public IconGroupSummaries iconGroupSummary() {
        return adminIconService.iconGroupSummary();
    }

    @GetMapping(value = "/summary", params = {"year", "month"})
    public IconGroupSummaries iconGroupSummary(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month) {
        return adminIconService.iconGroupSummaryByYearMonth(year, month);
    }

    @GetMapping("/monthly-revenue")
    public IconGroupMonthlyRevenues iconGroupMonthlyRevenue(@RequestParam(value = "year") int year) {
        return adminIconService.iconGroupMonthlyRevenue(year);
    }

}
