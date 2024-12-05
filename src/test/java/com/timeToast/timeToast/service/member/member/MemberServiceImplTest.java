package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoManagerResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Mock
    FileUploadService fileUploadService;

    @Mock
    CreatorAccountRepository creatorAccountRepository;

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

    private Member setUpCreator() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.CREATOR)
                .build();
    }

    private List<Member> setUpCreators(){
        List<Member> members = new ArrayList<>();
        for(long i=1; i<5; i++){
            Member member = Member.builder()
                            .premiumId(1L)
                            .email("test@gmail.com")
                            .nickname("testNickname"+i)
                            .memberProfileUrl("testProfileUrl")
                            .loginType(LoginType.GOOGLE)
                            .memberRole(MemberRole.CREATOR)
                            .build();
            ReflectionTestUtils.setField(member, "id", i);
            members.add(member);
        }
        return members;
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

    private CreatorAccount creatorAccountSetUp(){
        return CreatorAccount.builder()
                .memberId(1L)
                .bank(Bank.IBK)
                .accountNumber("accountNumber")
                .build();
    }

    @Test
    @DisplayName("프로필 이미지 등록")
    public void saveProfileImageByLogin(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        MultipartFile profileImage = mock(MultipartFile.class);

        String fileUrl = "fileUrl";

        when(fileUploadService.uploadfile(any(), any())).thenReturn(fileUrl);

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveProfileImageByLogin(1L,profileImage );

        //then
        assertEquals(fileUrl,memberInfoResponse.profileUrl());
    }

    @Test
    @DisplayName("닉네임 저장하기")
    public void postNicknameTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        String newNickname = "testNick";
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
    @DisplayName("로그인한 유저 프로필 조회")
    public void getMemberProfileByLoginTest(){
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
        MemberProfileResponse memberProfileResponse = memberService.getMemberProfileByLogin(1L);

        //then
        assertEquals(member.getNickname(), memberProfileResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberProfileResponse.profileUrl());
        assertEquals(following.size(), memberProfileResponse.followerCount());
        assertEquals(followers.size(), memberProfileResponse.followingCount());
        assertEquals(isFollowing, memberProfileResponse.isFollow());
        assertEquals(teamMembers.size(), memberProfileResponse.teamCount());
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
        MemberPremium memberPremium = memberService.getMemberPremium(1L);

        //then
        assertEquals(premium.getId(), memberPremium.premiumId());
        assertEquals(premium.getPremiumType(), memberPremium.premiumType());
    }

    @Test
    @DisplayName("아이콘 제작자 정보 저장 실패")
    public void saveCreatorInfoFail(){
        Member member = setUpMember();

        MultipartFile profileImage = mock(MultipartFile.class);
        CreatorRequest creatorRequest = mock(CreatorRequest.class);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> memberService.saveCreatorInfo(1L, profileImage, creatorRequest));

    }

    @Test
    @DisplayName("제작자 리스트 조회")
    public void getCreators(){
        //given
        List<Member> members = setUpCreators();
        when(memberRepository.findAllByMemberRole(MemberRole.CREATOR)).thenReturn(members);

        //when
        CreatorResponses creatorResponses = memberService.getCreators();

        //then
        assertEquals(members.size(), creatorResponses.creatorResponses().size());
    }

    @Test
    @DisplayName("제작자 id로 제작자 조회")
    public void getCreatorByCreatorId(){
        //given
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);

        CreatorAccount creatorAccount = creatorAccountSetUp();
        when(creatorAccountRepository.findByMemberId(1L)).thenReturn(Optional.of(creatorAccount));


        //when
        CreatorDetailResponse creatorDetailResponse = memberService.getCreatorByCreatorId(1L);

        //then
        assertEquals(creator.getMemberProfileUrl(), creatorDetailResponse.profileUrl());
        assertEquals(creator.getNickname(), creatorDetailResponse.nickname());
        assertEquals(creatorAccount.getAccountNumber(), creatorDetailResponse.accountNumber());
        assertEquals(creatorAccount.getBank().value(), creatorDetailResponse.bank());

    }

    @Test
    @DisplayName("관리자 사용자 목록 조회")
    public void getMembersForManager(){
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(creator));

        MemberManagerResponses memberManagerResponses = memberService.getMembersForManagers();

        assertThat(memberManagerResponses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 정보 조회 실패")
    public void getMemberInfoForManagerFail(){
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> memberService.getMemberInfoForManager(1L));
    }

    @Test
    @DisplayName("관리자 사용자 정보 조회 실패")
    public void getMemberInfoForManagerSuccess(){
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(member);

        Premium premium = setUpPremium();
        when(premiumRepository.getById(anyLong())).thenReturn(premium);

        when(followRepository.findAllByFollowingId(anyLong())).thenReturn(getFollowers());
        when(followRepository.findAllByFollowerId(anyLong())).thenReturn(getFollowing());
        when(teamMemberRepository.findAllByMemberId(anyLong())).thenReturn(getTeams());

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> memberService.getMemberInfoForManager(1L));
    }
}
