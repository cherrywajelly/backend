package com.timeToast.timeToast.controller.iconGroup;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponses;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/iconGroups")
@RestController
public class IconGroupManagerController {

    private final IconGroupAdminService iconGroupAdminService;

    public IconGroupManagerController(IconGroupAdminService iconGroupAdminService) {
        this.iconGroupAdminService = iconGroupAdminService;
    }

    @PostMapping("")
    public IconGroupInfoResponse saveIconState(@RequestBody IconGroupStateRequest iconGroupStateRequest) {
        return iconGroupAdminService.saveIconState(iconGroupStateRequest);
    }

    @GetMapping("/{iconGroupId}")
    public IconGroupInfoResponses iconGroupDetail(@PathVariable String iconGroupId) {
        return iconGroupAdminService.();
    }

    @GetMapping("")
    public IconGroupInfoResponses iconGroupList() {
        return iconGroupAdminService.getAllIconGroups();
    }

    @GetMapping("/non-approval")
    public IconGroupInfoResponses iconGroupNonApproval() {
        return iconGroupAdminService.getIconGroupForNonApproval();
    }

}
