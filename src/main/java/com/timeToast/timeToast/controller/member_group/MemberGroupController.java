package com.timeToast.timeToast.controller.member_group;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.team.TeamService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/groups")
@RestController
public class MemberGroupController {

    private final TeamService memberGroupService;

    public MemberGroupController(final TeamService memberGroupService) {
        this.memberGroupService = memberGroupService;
    }

    @PostMapping("")
    public TeamResponse saveGroup(@Login final LoginMember loginMember, @RequestBody final TeamSaveRequest memberGroupSaveRequest){
        return memberGroupService.saveTeam(loginMember.id(), memberGroupSaveRequest);
    }

    @PostMapping("/{groupId}/image")
    public TeamResponse saveGroupImage(@PathVariable final long groupId, @RequestPart final MultipartFile groupImage){
        return memberGroupService.saveTeamImage(groupId, groupImage);
    }

    @GetMapping("")
    public TeamResponses findGroupList(@Login final LoginMember loginMember){
        return memberGroupService.findLoginMemberTeams(loginMember.id());
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@Login final LoginMember loginMember, @PathVariable final  long groupId){
        memberGroupService.deleteMemberGroup(loginMember.id(), groupId);
    }

}
