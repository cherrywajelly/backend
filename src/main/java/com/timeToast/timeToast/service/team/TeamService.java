package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import org.springframework.web.multipart.MultipartFile;

public interface TeamService {

    TeamResponse saveTeam(final long memberId, final TeamSaveRequest groupSaveRequest);

    TeamResponse saveTeamImage(final long teamId, final MultipartFile teamProfileImage);

    TeamResponses findLoginMemberTeams(final long memberId);

    void deleteTeam(final long memberId, final long teamId);
    void deleteAllTeam(final long memberId);

}
