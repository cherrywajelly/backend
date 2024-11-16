package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class TeamServiceTest implements TeamService {
    @Override
    public TeamResponse saveTeam(long memberId, TeamSaveRequest groupSaveRequest) {
        return new TeamResponse(1,"team1","profile");
    }

    @Override
    public TeamResponse saveTeamImage(long teamId, MultipartFile multipartFile) {
        return new TeamResponse(1,"team1","profile1");
    }

    @Override
    public TeamResponses findLoginMemberTeams(long memberId) {
        List<TeamResponse> teamResponses = new ArrayList<>();
        teamResponses.add(new TeamResponse(1,"team1","profile1"));
        return new TeamResponses(teamResponses);
    }

    @Override
    public Response deleteTeam(long memberId, long teamId) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }

    @Override
    public void deleteAllTeam(long memberId) {

    }
}
