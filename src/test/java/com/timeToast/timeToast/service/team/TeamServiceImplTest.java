package com.timeToast.timeToast.service.team;


import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest  {


    @Mock
    TeamRepository teamRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    TeamMemberRepository teamMemberRepository;

    @Mock
    FileUploadService fileUploadService;

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

    private TeamMember setUpTeamMember(long memberId, long teamId){
        return TeamMember.builder()
                .memberId(memberId)
                .teamId(teamId)
                .build();
    }

    private List<TeamMember> setUpTeamMembers(){
        List<TeamMember> teamMembers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {

            TeamMember teamMember = setUpTeamMember(1L, i);
            ReflectionTestUtils.setField(teamMember, "createdAt", LocalDateTime.now());
            teamMembers.add(teamMember);
        }
        return teamMembers;
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
        assertEquals(team.getTeamProfileUrl(),teamResponse.teamProfileUrl());
        assertEquals(team.getName(), teamResponse.teamName());
        verify(teamRepository, times(1)).save(any(Team.class));
        verify(teamMemberRepository, times(teamMembers.size()+1)).save(any(TeamMember.class));
    }



    @Test
    @DisplayName("팀 대표 이미지 업로드 로직")
    public void saveTeamImage(){

        //given
        Team team = setUpTeam();
        ReflectionTestUtils.setField(team, "id", 1L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        MultipartFile teamImage = mock(MultipartFile.class);

        when(fileUploadService.uploadfile(any(), any())).thenReturn("file url");

        //when
        TeamResponse teamResponse = teamService.saveTeamImage(1L,teamImage );

        //then
        assertEquals(team.getId(), teamResponse.teamId());
        assertEquals(team.getName(), teamResponse.teamName());
        verify(fileUploadService, times(1)).uploadfile(any(), any());

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
        assertEquals(teamMembers.size(), teamResponses.teamResponses().size());

    }

    @Test
    @DisplayName("로그인한 사용자의 팀 삭제")
    public void deleteTeam(){
        //given
        TeamMember teamMember = setUpTeamMember(11L, 1L);
        when(teamMemberRepository.findByMemberIdAndTeamId(anyLong(), anyLong())).thenReturn(Optional.of(teamMember));

        when(teamMemberRepository.findAllByTeamId(anyLong())).thenReturn(setUpTeamMembers());

        //when
        Response response = teamService.deleteTeam(11L, 1L);

        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("로그인한 사용자의 팀 삭제")
    public void deleteAllTeam(){
        //given
        List<TeamMember> teamMembers = setUpTeamMembers();
        when(teamMemberRepository.findAllByMemberId(1L)).thenReturn(teamMembers);
        when(teamMemberRepository.findAllByTeamId(anyLong())).thenReturn(List.of());

        //when
        teamService.deleteAllTeam(1L);

        //then
        verify(teamMemberRepository, times(teamMembers.size())).delete(any(TeamMember.class));
        verify(teamMemberRepository, times(teamMembers.size())).findAllByTeamId(anyLong());
        verify(teamRepository, times(teamMembers.size())).deleteByTeamId(anyLong());

    }

    @Test
    @DisplayName("관리자 그룹 목록 조회 성공")
    public void getTeamForManager(){
        //given
        Team team = setUpTeam();
        ReflectionTestUtils.setField(team, "id", 1L);
        ReflectionTestUtils.setField(team, "createdAt", LocalDateTime.of(2024, 1, 1, 0, 0));
        when(teamMemberRepository.findAllByTeamId(anyLong())).thenReturn(setUpTeamMembers());
        when(teamRepository.findAll()).thenReturn(List.of(team));


        //when
        TeamManagerResponses teamManagerResponses = teamService.getTeamForManager();

        //then
        assertThat(teamManagerResponses).isNotNull();
    }

    @Test
    @DisplayName("관리자 그룹 상세 조회 성공")
    public void getTeamInfoForManager(){
        //given
        Team team = setUpTeam();
        Member member = setUpMember();
        ReflectionTestUtils.setField(team, "id", 1L);
        ReflectionTestUtils.setField(team, "createdAt", LocalDateTime.of(2024, 1, 1, 0, 0));
        when(teamMemberRepository.findAllByTeamId(anyLong())).thenReturn(setUpTeamMembers());

        ReflectionTestUtils.setField(member, "id", 1L);
        when(teamRepository.findAll()).thenReturn(List.of(team));


        //when
        TeamManagerResponses teamManagerResponses = teamService.getTeamForManager();

        //then
        assertThat(teamManagerResponses).isNotNull();
    }

}
