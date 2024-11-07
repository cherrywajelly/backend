package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final FileUploadService fileUploadService;

    public TeamServiceImpl(final TeamRepository teamRepository, final MemberRepository memberRepository,
                           final TeamMemberRepository teamMemberRepository, final FileUploadService fileUploadService) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.fileUploadService = fileUploadService;
    }

    @Transactional
    @Override
    public TeamResponse saveTeam(final long memberId, final TeamSaveRequest teamSaveRequest) {

        //save group
        Team team = Team.builder()
                .name(teamSaveRequest.teamName())
                .build();

        Team saveTeam = teamRepository.save(team);

        //save member_group
        teamMemberRepository.save(
                TeamMember.builder()
                        .teamId(saveTeam.getId())
                        .memberId(memberId)
                        .build()
        );

        List<Long> teamMembers = teamSaveRequest.teamMembers();

        teamMembers.forEach(
                (groupMemberId) -> {
                    Member findMember = memberRepository.findById(groupMemberId).orElseThrow( () -> new BadRequestException(MEMBER_NOT_EXISTS.getMessage()));

                    teamMemberRepository.save(
                            TeamMember.builder()
                                    .teamId(saveTeam.getId())
                                    .memberId(findMember.getId())
                                    .build()
                    );
                }
        );

        return TeamResponse.from(saveTeam);
    }

    @Transactional
    @Override
    public TeamResponse saveTeamImage(final long teamId, final  MultipartFile multipartFile) {

        //s3 로직
        String groupProfileUrl = "";

        Team team = teamRepository.findById(teamId).orElseThrow(()->
                new NotFoundException(TEAM_NOT_FOUND.getMessage())
        );

        team.updateTeamProfileUrl(groupProfileUrl);

        return TeamResponse.from(team);
    }

    @Transactional
    @Override
    public TeamResponses findLoginMemberTeams(final long memberId) {

        List<TeamMember> teamMembers = teamMemberRepository.findAllByMemberId(memberId);
        List<TeamResponse> teamResponses = new ArrayList<>();

        teamMembers.forEach(
                team -> {
                    Optional<Team> findTeam = teamRepository.findById(team.getTeamId());
                    if(findTeam.isPresent()){
                        teamResponses.add(
                                TeamResponse.from(findTeam.get())
                        );
                    }

                }
        );

        return new TeamResponses(teamResponses);
    }

    @Transactional
    @Override
    public void deleteTeam(final long memberId, final long teamId) {
        TeamMember teamMember = teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId).orElseThrow(()-> new NotFoundException(TEAM_MEMBER_NOT_FOUND.getMessage()));
        teamMemberRepository.delete(teamMember);

        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeamId(teamId);

        if(teamMembers.isEmpty()){
            teamRepository.deleteByTeamId(teamId);
        }

    }

}
