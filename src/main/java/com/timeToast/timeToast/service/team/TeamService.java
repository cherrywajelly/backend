package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.dto.team.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.team.response.TeamInfoManagerResponse;
import com.timeToast.timeToast.dto.team.response.TeamManagerResponses;
import com.timeToast.timeToast.dto.team.response.TeamResponse;
import com.timeToast.timeToast.dto.team.response.TeamResponses;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface TeamService {

    TeamResponse saveTeam(final long memberId, final TeamSaveRequest groupSaveRequest);
    TeamResponse saveTeamImage(final long teamId, final MultipartFile teamProfileImage);
    TeamResponses findLoginMemberTeams(final long memberId);
    Response deleteTeam(final long memberId, final long teamId);
    void deleteAllTeam(final long memberId);
    TeamManagerResponses getTeamForManager();
    TeamInfoManagerResponse getTeamInfoForManager(final long teamId);
}
