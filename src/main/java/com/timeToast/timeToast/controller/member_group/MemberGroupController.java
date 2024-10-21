package com.timeToast.timeToast.controller.member_group;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.member_group.request.MemberGroupSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponse;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.member_group.MemberGroupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/group")
@RestController
public class MemberGroupController {

    private final MemberGroupService memberGroupService;

    public MemberGroupController(final MemberGroupService memberGroupService) {
        this.memberGroupService = memberGroupService;
    }

    @PostMapping("")
    public MemberGroupResponse saveGroup(@Login final LoginMember loginMember, @RequestBody final MemberGroupSaveRequest memberGroupSaveRequest){
        return memberGroupService.saveGroup(loginMember.id(), memberGroupSaveRequest);
    }

    @PostMapping("/{groupId}/image")
    public MemberGroupResponse saveGroupImage(@PathVariable final long groupId, @RequestPart final MultipartFile groupImage){
        return memberGroupService.saveGroupImage(groupId, groupImage);
    }

    @GetMapping("/groups")
    public MemberGroupResponses findGroupList(@Login final LoginMember loginMember){
        return memberGroupService.findLoginMemberGroups(loginMember.id());
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@Login final LoginMember loginMember, @PathVariable final  long groupId){
        memberGroupService.deleteMemberGroup(loginMember.id(), groupId);
    }

}
