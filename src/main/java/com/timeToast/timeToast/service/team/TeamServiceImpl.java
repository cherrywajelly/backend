package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.BasicImage.*;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;

    public TeamServiceImpl(final TeamRepository teamRepository, final TeamMemberRepository teamMemberRepository,
                           final MemberRepository memberRepository, final FileUploadService fileUploadService) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.memberRepository = memberRepository;
        this.fileUploadService = fileUploadService;
    }

    @Value("$spring.cloud.oci.base-url}")
    private String baseUrl;


    @Transactional
    @Override
    public TeamResponse saveTeam(final long memberId, final TeamSaveRequest teamSaveRequest) {

        Team team = Team.builder()
                .name(teamSaveRequest.teamName())
                .teamProfileUrl(BASIC_PROFILE_IMAGE_URL)
                .build();

        Team saveTeam = teamRepository.save(team);

        teamMemberRepository.save(
                TeamMember.builder()
                        .teamId(saveTeam.getId())
                        .memberId(memberId)
                        .build()
        );

        List<Long> teamMembers = teamSaveRequest.teamMembers();

        teamMembers.forEach(
                (teamMemberId) -> {
                    Member findMember = memberRepository.findById(teamMemberId).orElseThrow( () -> new BadRequestException(MEMBER_NOT_FOUND.getMessage()));

                    teamMemberRepository.save(
                            TeamMember.builder()
                                    .teamId(saveTeam.getId())
                                    .memberId(findMember.getId())
                                    .build()
                    );
                }
        );

        log.info("save team {} by {}", team.getId(), memberId);

        return TeamResponse.from(saveTeam);
    }

    @Transactional
    @Override
    public TeamResponse saveTeamImage(final long teamId, final  MultipartFile teamProfileImage) {

        Team team = teamRepository.findById(teamId).orElseThrow(()->
                new NotFoundException(TEAM_NOT_FOUND.getMessage())
        );

        String teamUrl = baseUrl +  TEAM.value() + SLASH.value() + IMAGE.value() + SLASH.value() +  team.getId();
        String teamProfileUrl = fileUploadService.uploadfile(teamProfileImage, teamUrl);

        team.updateTeamProfileUrl(teamProfileUrl);

        log.info("save team Image {}", team.getId());
        return TeamResponse.from(team);
    }

    @Transactional(readOnly = true)
    @Override
    public TeamResponses findLoginMemberTeams(final long memberId) {

        List<TeamMember> teamMembers = teamMemberRepository.findAllByMemberId(memberId);
        List<TeamResponse> teamResponses = new ArrayList<>();

        teamMembers.forEach(
                team -> teamResponses.add(TeamResponse.from(teamRepository.getById(team.getTeamId()))));

        return new TeamResponses(teamResponses);
    }

    @Transactional
    @Override
    public Response deleteTeam(final long memberId, final long teamId) {
        TeamMember teamMember = teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId)
                .orElseThrow(()-> new NotFoundException(TEAM_MEMBER_NOT_FOUND.getMessage()));

        teamMemberRepository.delete(teamMember);

        if(teamMemberRepository.findAllByTeamId(teamId).isEmpty()){
            log.info("delete team {}", teamId);
            teamRepository.deleteByTeamId(teamId);
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());

    }

    @Transactional
    @Override
    public void deleteAllTeam(final long memberId) {

        teamMemberRepository.findAllByMemberId(memberId).forEach(
                teamMember -> {
                    teamMemberRepository.delete(teamMember);
                    if(teamMemberRepository.findAllByTeamId(teamMember.getTeamId()).isEmpty()){
                        teamRepository.deleteByTeamId(teamMember.getTeamId());
                    }
                }
        );

    }

}
