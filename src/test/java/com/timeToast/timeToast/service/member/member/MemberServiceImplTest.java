package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    FollowRepository followRepository;

    @Mock
    TeamMemberRepository teamMemberRepository;

    @Mock
    PremiumRepository premiumRepository;

    @InjectMocks
    MemberServiceImpl memberService;

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

    private List<Follow> getFollowers(){
        List<Follow> follows = new ArrayList<>();
        for(int i=0; i<10; i++){
            follows.add(Follow.builder().followingId(i).followerId(i+1).build());
        }
        return follows;
    }

    private List<Follow> getFollowing(){
        List<Follow> follows = new ArrayList<>();
        for(int i=0; i<5; i++){
            follows.add(Follow.builder().followingId(i).followerId(i+2).build());
        }
        return follows;
    }

    private Follow getFollow(){
        return Follow.builder().followingId(1L).followerId(2L).build();
    }

    private List<TeamMember> getTeams(){
        List<TeamMember> teamMembers = new ArrayList<>();
        for(int i=0; i<5; i++){
            teamMembers.add(TeamMember.builder().memberId(1L).teamId(i).build());
        }
        return teamMembers;
    }

    private Premium setUpPremium(){
        return Premium.builder()
                .premiumType(PremiumType.BASIC)
                .price(0)
                .count(0)
                .description("basic")
                .build();
    }

    @Test
    @DisplayName("닉네임 저장하기")
    public void postNicknameTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        String newNickname = "testNewNickname";
        //when
        MemberInfoResponse memberInfoResponse = memberService.postNickname(newNickname, member.getId());

        //then
        assertEquals(newNickname, memberInfoResponse.nickname());
    }

    @Test
    @DisplayName("닉네임 유효성 확인 - 성공")
    public void nicknameValidationTestSuccess(){
        //given
        when(memberRepository.existsByNickname(any(String.class))).thenReturn(false);

        //when
        Response response = memberService.nicknameValidation("nickname10");

        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("닉네임 유효성 확인 - 실패")
    public void nicknameValidationTestFailure(){
        //given
        when(memberRepository.existsByNickname(any(String.class))).thenReturn(true);

        //when, then
        assertThrows(ConflictException.class, () -> memberService.nicknameValidation("nickname6"));
    }

    @Test
    @DisplayName("유저 info 조회")
    public void getMemberInfoTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        //when
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(1L);

        //then
        assertEquals(member.getNickname(), memberInfoResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberInfoResponse.profileUrl());
    }



    @Test
    @DisplayName("유저 프로필 조회")
    public void getMemberProfileTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);
        List<Follow> followers = getFollowers();
        List<Follow> following = getFollowing();
        List<TeamMember> teamMembers = getTeams();
        boolean isFollowing = true;

        when(followRepository.findAllByFollowerId(any(Long.class))).thenReturn(followers);
        when(followRepository.findAllByFollowingId(any(Long.class))).thenReturn(following);
        when(followRepository.findByFollowingIdAndFollowerId(any(Long.class),any(Long.class))).thenReturn(Optional.of(getFollow()));

        when(teamMemberRepository.findAllByMemberId(any(Long.class))).thenReturn(teamMembers);

        //when
        MemberProfileResponse memberProfileResponse = memberService.getMemberProfile(1L, 2L);

        //then
        assertEquals(member.getNickname(), memberProfileResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberProfileResponse.profileUrl());
        assertEquals(following.size(), memberProfileResponse.followerCount());
        assertEquals(followers.size(), memberProfileResponse.followingCount());
        assertEquals(isFollowing, memberProfileResponse.isFollow());
        assertEquals(teamMembers.size(), memberProfileResponse.teamCount());
    }



    @Test
    @DisplayName("유저 멤버십 조회")
    public void getMemberPremiumTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(any(Long.class))).thenReturn(premium);

        //when
        PremiumResponse premiumResponse = memberService.getMemberPremium(1L);

        //then
        assertEquals(premium.getId(), premiumResponse.premiumId());
        assertEquals(premium.getPremiumType(), premiumResponse.premiumType());
        assertEquals(premium.getPrice(), premiumResponse.price());
        assertEquals(premium.getCount(), premiumResponse.count());
        assertEquals(premium.getDescription(), premiumResponse.description());
    }

}
