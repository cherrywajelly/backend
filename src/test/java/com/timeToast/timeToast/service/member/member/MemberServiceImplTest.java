package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
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
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    FollowRepository followRepository;


    @Mock
    PremiumRepository premiumRepository;

    @Mock
    FileUploadService fileUploadService;

    @Mock
    IconGroupAdminService iconGroupAdminService;

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
                .premiumId(1L)
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
                .premiumId(1L)
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
                            .premiumId(1L)
                            .build();
            ReflectionTestUtils.setField(member, "id", i);
            members.add(member);
        }
        return members;
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

    private com.timeToast.timeToast.domain.premium.Premium setUpPremium(){
        return com.timeToast.timeToast.domain.premium.Premium.builder()
                .premiumType(PremiumType.BASIC)
                .price(0)
                .count(0)
                .description("basic")
                .build();
    }

    @Test
    @DisplayName("관리자 role staff로 변환")
    public void saveToStaff(){
        //given
        Member member = Member.builder()
                .memberRole(MemberRole.USER)
                .premiumId(1L)
                .build();

        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(1L)).thenReturn(premium);

        assertEquals(MemberRole.USER, member.getMemberRole());

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveToStaff(member.getId());


        //then
        assertEquals(MemberRole.STAFF, member.getMemberRole());
        assertEquals(MemberRole.STAFF, memberInfoResponse.memberRole());
    }

    @Test
    @DisplayName("관리자 role creators로 변환")
    public void saveToCreators(){
        //given
        Member member = Member.builder()
                .memberRole(MemberRole.USER)
                .premiumId(1L)
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(1L)).thenReturn(premium);

        assertEquals(MemberRole.USER, member.getMemberRole());

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveToCreators(member.getId());

        //then
        assertEquals(MemberRole.CREATOR, member.getMemberRole());
        assertEquals(MemberRole.CREATOR, memberInfoResponse.memberRole());
    }

    @Test
    @DisplayName("관리자 role user로 변환")
    public void saveToUser(){
        //given
        Member member = Member.builder()
                .memberRole(MemberRole.CREATOR)
                .premiumId(1L)
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(1L)).thenReturn(premium);

        assertEquals(MemberRole.CREATOR, member.getMemberRole());

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveToUser(member.getId());


        //then
        assertEquals(MemberRole.USER, member.getMemberRole());
        assertEquals(MemberRole.USER, memberInfoResponse.memberRole());
    }

    @Test
    @DisplayName("프로필 이미지 등록")
    public void saveProfileImage(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        when(memberRepository.getById(any(Long.class))).thenReturn(member);


        MultipartFile profileImage = mock(MultipartFile.class);
        String fileUrl = "fileUrl";

        when(fileUploadService.uploadfile(any(), any())).thenReturn(fileUrl);

        Premium premium = new Premium(PremiumType.BASIC, 100, 10, "description");
        ReflectionTestUtils.setField(premium, "id", 1L);

        when(premiumRepository.getById(1L)).thenReturn(premium);

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveProfileImage(1L,profileImage );

        //then
        assertEquals(fileUrl,memberInfoResponse.profileUrl());
    }

    @Test
    @DisplayName("닉네임 저장하기")
    public void saveNicknameTest(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(any(Long.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        String newNickname = "testNick";

        Premium premium = new Premium(PremiumType.BASIC, 100, 10, "description");
        ReflectionTestUtils.setField(premium, "id", 1L);

        when(premiumRepository.getById(1L)).thenReturn(premium);

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveNickname(newNickname, member.getId());

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
        ReflectionTestUtils.setField(member, "id", 1L);

        when(memberRepository.getById(any(Long.class))).thenReturn(member);

        Premium premium = new Premium(PremiumType.BASIC, 100, 10, "description");
        ReflectionTestUtils.setField(premium, "id", 1L);

        when(premiumRepository.getById(1L)).thenReturn(premium);

        //when
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(1L);

        //then
        assertEquals(member.getNickname(), memberInfoResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberInfoResponse.profileUrl());
    }

    @Test
    @DisplayName("로그인한 유저 프로필 조회")
    public void getMemberProfileTest(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        when(memberRepository.getById(any(Long.class))).thenReturn(member);


        boolean isFollowing = true;

        when(followRepository.findByFollowingIdAndFollowerId(any(Long.class),any(Long.class))).thenReturn(Optional.of(getFollow()));

        //when
        MemberProfileResponse memberProfileResponse = memberService.getMemberProfile(1L);

        //then
        assertEquals(member.getNickname(), memberProfileResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberProfileResponse.profileUrl());
        assertEquals(isFollowing, memberProfileResponse.isFollow());
    }



    @Test
    @DisplayName("유저 멤버십 조회")
    public void getMemberPremiumByMemberTest(){
        //given
        Member member = setUpMember();

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(any(Long.class))).thenReturn(premium);

        //when
        MemberPremium memberPremium = memberService.getMemberPremiumByMember(member);

        //then
        assertEquals(premium.getId(), memberPremium.premiumId());
        assertEquals(premium.getPremiumType(), memberPremium.premiumType());
    }

    @Test
    @DisplayName("아이콘 제작자 정보 저장 실패")
    public void saveCreatorInfoFail(){
        CreatorAccount creatorAccount = mock(CreatorAccount.class);
        assertThrows(NullPointerException.class, () -> memberService.saveCreatorInfo(1L, creatorAccount));
    }

    @Test
    @DisplayName("아이콘 제작자 정보 저장 실패 - 저장된 계좌 정보가 없을 경우")
    public void saveCreatorInfoSuccess(){
        Member creator = setUpMember();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);

        CreatorAccount creatorAccount =  new CreatorAccount(Bank.IBK, "accountNumber");

        CreatorInfoResponse response = memberService.saveCreatorInfo(1L, creatorAccount);

        assertThat(response.bank()).isEqualTo(creatorAccount.bank());
        assertThat(response.accountNumber()).isEqualTo(creatorAccount.accountNumber());
    }

    @Test
    @DisplayName("제작자 리스트 조회")
    public void getCreators(){
        //given
        List<Member> members = setUpCreators();
        when(memberRepository.findAllByMemberRole(MemberRole.CREATOR)).thenReturn(members);

        List<CreatorIconInfo> creatorIconInfoList = List.of(
                CreatorIconInfo.builder()
                        .title("title")
                        .revenue(1000)
                        .salesCount(1)
                        .iconImageUrl(List.of("iconImageUrl"))
                        .build()
        );
        CreatorIconInfos creatorIconInfos =  new CreatorIconInfos(1, 1000, 10,creatorIconInfoList);
        when(iconGroupAdminService.getIconGroupsByCreator(any(Long.class))).thenReturn(creatorIconInfos);

        //when
        CreatorResponses creatorResponses = memberService.getCreators();

        //then
        assertEquals(members.size(), creatorResponses.creatorResponses().size());
    }

    @Test
    @DisplayName("제작자 id로 제작자 조회")
    public void getCreatorInfo(){
        //given
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);

        //when
        CreatorInfoResponse creatorInfoResponse = memberService.getCreatorInfo(1L);

        //then
        assertEquals(creator.getMemberProfileUrl(), creatorInfoResponse.profileUrl());
        assertEquals(creator.getNickname(), creatorInfoResponse.nickname());
        assertEquals(creator.getAccountNumber(), creatorInfoResponse.accountNumber());
        assertEquals(creator.getBank(), creatorInfoResponse.bank());

    }



}
