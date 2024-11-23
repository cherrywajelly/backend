package com.timeToast.timeToast.service.team;


import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest  {


    @Mock
    TeamRepository teamRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    TeamMemberRepository teamMemberRepository;

    @InjectMocks
    TeamServiceImpl teamService;


    private Team setUpTeam(){
        return Team.builder()
                .name("teamName")
                .teamProfileUrl("teamProfileUrl")
                .build();
    }

    private Member setUpMember() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.USER)
                .build();
    }

    @Test
    @DisplayName("팀 저장 테스트 - 성공")
    public void saveTeamTest(){

        //given
        Team team = setUpTeam();
        ReflectionTestUtils.setField(team, "id", 1L);
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        List<Long> teamMembers = new ArrayList<>();
        teamMembers.add(1L);
        teamMembers.add(2L);
        teamMembers.add(3L);

        Member member1 = setUpMember();
        ReflectionTestUtils.setField(member1, "id", 1L);
        Member member2 = setUpMember();
        ReflectionTestUtils.setField(member2, "id", 2L);
        Member member3 = setUpMember();
        ReflectionTestUtils.setField(member3, "id", 3L);
        when(memberRepository.findById(member1.getId())).thenReturn(Optional.of(member1));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        when(memberRepository.findById(member3.getId())).thenReturn(Optional.of(member3));

        TeamSaveRequest teamSaveRequest = new TeamSaveRequest("teamName",teamMembers);

        //when
        TeamResponse teamResponse = teamService.saveTeam(1L, teamSaveRequest);

        //then
        assertEquals(teamResponse.teamProfileUrl(), team.getTeamProfileUrl());
        assertEquals(teamResponse.teamName(), team.getName());
        verify(teamRepository, times(1)).save(any(Team.class));
        verify(teamMemberRepository, times(teamMembers.size()+1)).save(any(TeamMember.class));
    }

    private TeamMember setUpTeamMember(long memberId, long teamId){
        return TeamMember.builder()
                .memberId(memberId)
                .teamId(teamId)
                .build();
    }

    private List<TeamMember> setUpTeamMembers(){
        List<TeamMember> teamMembers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            teamMembers.add(setUpTeamMember(1L, i));
        }
        return teamMembers;
    }

    @Test
    @DisplayName("로그인한 사용자의 팀 리스트 조회 테스트")
    public void findLoginMemberTeams(){

        //given
        List<TeamMember> teamMembers = setUpTeamMembers();
        when(teamMemberRepository.findAllByMemberId(any(Long.class))).thenReturn(teamMembers);

        Team team = setUpTeam();
        ReflectionTestUtils.setField(team, "id", 1L);
        when(teamRepository.getById(any(Long.class))).thenReturn(team);

        //when
        TeamResponses teamResponses = teamService.findLoginMemberTeams(1L);

        //then
        assertEquals(teamResponses.teamResponses().size(), teamMembers.size());

    }



}
