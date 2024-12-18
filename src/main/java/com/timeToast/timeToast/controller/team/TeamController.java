package com.timeToast.timeToast.controller.team;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.team.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.team.response.TeamResponse;
import com.timeToast.timeToast.dto.team.response.TeamResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.team.TeamService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/teams")
@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("")
    public TeamResponse saveTeam(@Login final LoginMember loginMember, @RequestBody final TeamSaveRequest teamSaveRequest){
        return teamService.saveTeam(loginMember.id(), teamSaveRequest);
    }

    @PostMapping("/{teamId}/image")
    public TeamResponse saveGroupImage(@PathVariable final long teamId, @RequestPart final MultipartFile teamProfileImage){
        return teamService.saveTeamImage(teamId, teamProfileImage);
    }

    @GetMapping("")
    public TeamResponses findTeamList(@Login final LoginMember loginMember){
        return teamService.findLoginMemberTeams(loginMember.id());
    }

    @DeleteMapping("/{teamId}")
    public Response deleteTeam(@Login final LoginMember loginMember, @PathVariable final  long teamId){
        return teamService.deleteTeam(loginMember.id(), teamId);
    }

}
