package com.timeToast.timeToast.controller.group;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.group.request.GroupSaveRequest;
import com.timeToast.timeToast.dto.group.response.GroupResponse;
import com.timeToast.timeToast.dto.group.response.GroupResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.group.GroupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/group")
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(final GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("")
    public GroupResponse saveGroup(@Login final LoginMember loginMember, @RequestBody final GroupSaveRequest groupSaveRequest){
        return groupService.saveGroup(loginMember.id(), groupSaveRequest);
    }

    @PostMapping("/{groupId}/image")
    public GroupResponse saveGroupImage(@PathVariable final long groupId, @RequestPart final MultipartFile groupImage){
        return groupService.saveGroupImage(groupId, groupImage);
    }

    @GetMapping("/groups")
    public GroupResponses findGroupList(@Login final LoginMember loginMember){
        return groupService.findLoginMemberGroups(loginMember.id());
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@Login final LoginMember loginMember, @PathVariable final  long groupId){
        groupService.deleteGroup(loginMember.id(), groupId);
    }

}
