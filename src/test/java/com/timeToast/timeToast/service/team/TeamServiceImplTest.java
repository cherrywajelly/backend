package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.util.BaseServiceTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
public class TeamServiceImplTest extends BaseServiceTests {


    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @DisplayName("팀 저장 테스트 - 성공")
    public void saveTeam(){

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();
        Member member3 =  Member.builder().nickname("nickname3").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);
        Member saveMember3 = memberRepository.save(member3);

        //when
        Team saveTeam = teamRepository.save(
                Team.builder()
                        .name("teamName")
                        .build()
        );

        TeamMember teamMember1 = teamMemberRepository.save(
          TeamMember.builder()
                  .teamId(saveTeam.getId())
                  .memberId(saveMember1.getId()).build()
        );

        TeamMember teamMember2 = teamMemberRepository.save(
                TeamMember.builder()
                        .teamId(saveTeam.getId())
                        .memberId(saveMember2.getId()).build()
        );

        TeamMember teamMember3 = teamMemberRepository.save(
                TeamMember.builder()
                        .teamId(saveTeam.getId())
                        .memberId(saveMember3.getId()).build()
        );

        teamMemberRepository.save(teamMember1);
        teamMemberRepository.save(teamMember2);
        teamMemberRepository.save(teamMember3);

        assertThat(teamMember1.getMemberId()).isEqualTo(member1.getId());
        assertThat(teamMember2.getMemberId()).isEqualTo(member2.getId());
        assertThat(teamMember3.getMemberId()).isEqualTo(member3.getId());
    }

    @Test
    @DisplayName("팀 저장 테스트 - 실패")
    public void saveTeamFail(){

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);

        //when
        Team saveTeam = teamRepository.save(
                Team.builder()
                        .name("teamName")
                        .build()
        );


        assertThat(memberRepository.findById(3L)).isEmpty();

        //어떻게 오류 난 걸 쓰지
    }

    @Test
    @DisplayName("로그인한 사용자의 팀 리스트 조회 테스트")
    public void findLoginMemberTeams(){

    }



}
