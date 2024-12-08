package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.dto.team.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.team.response.TeamResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.follow.FollowService;
import com.timeToast.timeToast.service.image.FileUploadService;
import com.timeToast.timeToast.service.team.TeamService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class TeamIntegrationTest extends TestContainerSupport {

    private final TeamService teamService;
    private final FollowService followService;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;

    @Autowired
    public TeamIntegrationTest(final TeamService teamService, final FollowService followService,
                               final TeamRepository teamRepository,
                               final TeamMemberRepository teamMemberRepository, final MemberRepository memberRepository,
                               final FileUploadService fileUploadService) {
        this.teamService = teamService;
        this.followService = followService;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.memberRepository = memberRepository;
        this.fileUploadService = fileUploadService;
    }

    @Test
    @DisplayName("사용자는 자신의 팔로잉 유저와 팀을 만들 수 있다.")
    public void tryDeleteFollowings() {
        //login member
        Member member = memberRepository.getById(1L);
        FollowResponses memberFollowingList = followService.findFollowingList(member.getId());

        //save team
        List<Long> teamMembers = new ArrayList<>();
        for(int i=0; i<2; i++){
            teamMembers.add(memberFollowingList.followResponses().get(i).memberId());
        }

        TeamSaveRequest teamSaveRequest = new TeamSaveRequest("team", teamMembers);
        TeamResponse teamResponse = teamService.saveTeam(member.getId(), teamSaveRequest);

        //teamMember
        List<TeamMember> savedTeamMember = teamMemberRepository.findAllByTeamId(teamResponse.teamId());
        Assertions.assertEquals(teamMembers.size()+1, savedTeamMember.size());

    }

}
