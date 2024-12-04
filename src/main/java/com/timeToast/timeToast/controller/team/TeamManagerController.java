package com.timeToast.timeToast.controller.team;

import com.timeToast.timeToast.dto.member_group.response.TeamManagerResponses;
import com.timeToast.timeToast.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v3/teams")
@RestController
@RequiredArgsConstructor
public class TeamManagerController {
    private final TeamService teamService;

    @GetMapping("")
    public TeamManagerResponses getTeamsManager() {
        return teamService.getTeamForManager();
    }


}
