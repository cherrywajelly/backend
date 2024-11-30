package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.TEAM_MEMBER_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.TEAM_NOT_FOUND;

public class TeamServiceTest implements TeamService {
    @Override
    public TeamResponse saveTeam(long memberId, TeamSaveRequest groupSaveRequest) {
        return new TeamResponse(1,"team1","profile");
    }

    @Override
    public TeamResponse saveTeamImage(final long teamId, final MultipartFile multipartFile) {
        if(teamId==2){
            throw  new NotFoundException(TEAM_NOT_FOUND.getMessage());
        }
        return new TeamResponse(1,"team1","profile1");
    }

    @Override
    public TeamResponses findLoginMemberTeams(final long memberId) {
        List<TeamResponse> teamResponses = new ArrayList<>();
        teamResponses.add(new TeamResponse(1,"team1","profile1"));
        return new TeamResponses(teamResponses);
    }

    @Override
    public Response deleteTeam(final long memberId, final long teamId) {
        if(teamId==2){
            throw  new NotFoundException(TEAM_MEMBER_NOT_FOUND.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }

    @Override
    public void deleteAllTeam(final long memberId) {

    }
}
