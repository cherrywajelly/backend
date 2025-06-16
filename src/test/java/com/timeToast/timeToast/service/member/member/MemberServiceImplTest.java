package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
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
import com.timeToast.timeToast.service.icon.AdminIconService;
import com.timeToast.timeToast.service.image.FileUploadService;
import net.bytebuddy.utility.RandomString;
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
    AdminIconService adminIconService;

    @InjectMocks
    MemberServiceImpl memberService;

    private Member getUser() {
        return Member.builder()
                .email("test@gmail.com")
                .nickname("testUser")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.USER)
                .premiumId(1L)
                .build();
    }

    private Member getCreator() {
        return Member.builder()
                .email("test@gmail.com")
                .nickname("testCreator")
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


    private Follow getFollow(long followingId, long followerId){
        return Follow.builder().followingId(followingId).followerId(followerId).build();
    }


    private Premium getBasicPremium(){
        return new Premium(PremiumType.BASIC, 0, 0, "basic");
    }

    @Test
    @DisplayName("관리자 role staff로 변환")
    public void saveToStaff(){
        //given
        Member member = getUser();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(member.getId())).thenReturn(member);

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

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
        Member creator = getCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

        assertEquals(MemberRole.CREATOR, creator.getMemberRole());

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveToCreators(creator.getId());

        //then
        assertEquals(MemberRole.CREATOR, creator.getMemberRole());
        assertEquals(MemberRole.CREATOR, memberInfoResponse.memberRole());
    }

    @Test
    @DisplayName("관리자 role user로 변환")
    public void saveToUser(){
        //given
        Member creator = getCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

        assertEquals(MemberRole.CREATOR, creator.getMemberRole());

        //when
        MemberInfoResponse memberInfoResponse = memberService.saveToUser(creator.getId());

        //then
        assertEquals(MemberRole.USER, creator.getMemberRole());
        assertEquals(MemberRole.USER, memberInfoResponse.memberRole());
    }

    @Test
    @DisplayName("프로필 이미지 등록")
    public void saveProfileImage(){
        //given
        Member member = getUser();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(member.getId())).thenReturn(member);

        MultipartFile profileImage = mock(MultipartFile.class);
        String fileUrl = "fileUrl";
        when(fileUploadService.uploadfile(any(), any())).thenReturn(fileUrl);

        Premium premium = getBasicPremium();
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
        Member member = getUser();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(member.getId())).thenReturn(member);

        String newNickname = "testNick";

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

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
        Response response = memberService.nicknameValidation(RandomString.make(6));

        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("닉네임 유효성 확인 - 실패")
    public void nicknameValidationTestFailure(){
        //given
        Member member = getUser();
        when(memberRepository.existsByNickname(member.getNickname())).thenReturn(true);

        //when, then
        assertThrows(ConflictException.class, () -> memberService.nicknameValidation(member.getNickname()));
    }

    @Test
    @DisplayName("유저 info 조회")
    public void getMemberInfoTest(){
        //given
        Member member = getUser();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(member.getId())).thenReturn(member);

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

        //when
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(member.getId());

        //then
        assertEquals(member.getNickname(), memberInfoResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), memberInfoResponse.profileUrl());
    }

    @Test
    @DisplayName("로그인한 유저 프로필 조회")
    public void getMemberProfileTest(){
        //given
        Member member = getUser();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(any(Long.class))).thenReturn(member);

        Follow follow = getFollow(member.getId(), member.getId());
        when(followRepository.findByFollowingIdAndFollowerId(any(Long.class),any(Long.class))).thenReturn(Optional.of(follow));

        //when
        MemberProfileResponse memberProfileResponse = memberService.getMemberProfile(1L);

        //then
        assertEquals(MemberProfileResponse.from(member, true), memberProfileResponse);
    }



    @Test
    @DisplayName("유저 멤버십 조회")
    public void getMemberPremiumByMemberTest(){
        //given
        Member member = getUser();

        Premium premium = getBasicPremium();
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(premium.getId())).thenReturn(premium);

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
    @DisplayName("아이콘 제작자 정보 저장 성공")
    public void saveCreatorInfoSuccess(){
        Member creator = getUser();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);

        CreatorAccount creatorAccount =  new CreatorAccount(Bank.IBK, "accountNumber");

        CreatorInfoResponse creatorInfoResponse = memberService.saveCreatorInfo(1L, creatorAccount);

        assertEquals(CreatorInfoResponse.from(creator), creatorInfoResponse);
    }

    //TODO 개선
    @Test
    @DisplayName("제작자 리스트 조회")
    public void getCreators(){
        //given
        List<Member> creators = setUpCreators();
        when(memberRepository.findAllByMemberRole(MemberRole.CREATOR)).thenReturn(creators);

        List<CreatorIconInfo> creatorIconInfoList = List.of(
                CreatorIconInfo.builder()
                        .title("title")
                        .revenue(1000)
                        .salesCount(1)
                        .iconImageUrl(List.of("iconImageUrl"))
                        .build()
        );
        CreatorIconInfos creatorIconInfos =  new CreatorIconInfos(1, 1000, 10,creatorIconInfoList);
        when(adminIconService.getIconGroupsByCreator(any(Long.class))).thenReturn(creatorIconInfos);

        //when
        CreatorResponses creatorResponses = memberService.getCreators();

        //then
        assertEquals(creators.size(), creatorResponses.creatorResponses().size());
    }

    @Test
    @DisplayName("제작자 id로 제작자 조회")
    public void getCreatorInfo(){
        //given
        Member creator = getCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);

        //when
        CreatorInfoResponse creatorInfoResponse = memberService.getCreatorInfo(creator.getId());

        //then
        assertEquals(CreatorInfoResponse.from(creator), creatorInfoResponse);

    }



}
