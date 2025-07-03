package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.jpa.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jpa.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import com.timeToast.timeToast.repository.jpa.payment.PaymentRepository;
import com.timeToast.timeToast.repository.jpa.premium.PremiumRepository;
import com.timeToast.timeToast.repository.jpa.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.jpa.team.team_member.TeamMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminMemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PremiumRepository premiumRepository;


    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private GiftToastRepository giftToastRepository;

    @Mock
    private ShowcaseRepository showcaseRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;


    @InjectMocks
    private AdminMemberServiceImpl managerService;

    @Mock
    MemberServiceImpl memberService;

    private Member member;
    private Premium premium;
    private Team team;
    private Icon icon;
    private IconGroup iconGroup;
    @BeforeEach
    void setUp() {
        long memberId = 1L;
        String name = "name";

        member = Member.builder().memberRole(MemberRole.USER).premiumId(1L).build();
        premium = Premium.builder().premiumType(PremiumType.PREMIUM).build();
        team = Team.builder().build();
        icon = Icon.builder().build();
        iconGroup = IconGroup.builder().name(name).build();
    }

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


    private List<Member> setUpCreators(){
        List<Member> members = new ArrayList<>();
        for(long i=1; i<5; i++){
            Member tempMember = Member.builder()
                    .premiumId(1L)
                    .email("test@gmail.com")
                    .nickname("testNickname"+i)
                    .memberProfileUrl("testProfileUrl")
                    .loginType(LoginType.GOOGLE)
                    .memberRole(MemberRole.CREATOR)
                    .premiumId(1L)
                    .build();
            ReflectionTestUtils.setField(tempMember, "id", i);
            members.add(tempMember);
        }
        return members;
    }


    @Test
    @DisplayName("관리자 사용자 목록 조회")
    public void getMembersForManager(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(member));

        ReflectionTestUtils.setField(premium, "id", 1L);

        MemberPremium memberPremium = new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
        when(memberService.getMemberPremiumByMember(member)).thenReturn(memberPremium);

        MemberInfoResponses memberInfoResponses = managerService.getMembersForManagers();

        assertThat(memberInfoResponses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 목록 조회 실패")
    public void getMembersForManagerFail(){
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(member));

        NullPointerException exception = assertThrows(NullPointerException.class, () -> managerService.getMembersForManagers());

        assertThat(exception).isNotNull();
    }

//    @Test
//    @DisplayName("관리자 사용자 정보 조회 실패")
//    public void getMemberInfoForManagerFail(){
//        ReflectionTestUtils.setField(member, "iconGroupId", 1L);
//        when(memberRepository.getById(anyLong())).thenReturn(null);
//
//        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.g(1L));
//    }
//
//    @Test
//    @DisplayName("관리자 사용자 정보 조회 성공")
//    public void getMemberInfoForManagerSuccess(){
//        ReflectionTestUtils.setField(member, "iconGroupId", 1L);
//        when(memberRepository.getById(anyLong())).thenReturn(member);
//
//        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.getMemberInfoForManager(1L));
//    }


    @Test
    @DisplayName("관리자 사용자 그룹 정보 조회 성공")
    public void getTeamSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        TeamDataManagerResponses responses = managerService.getMemberTeamInfo(1L);

        assertThat(responses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 그룹 정보 조회 성공")
    public void getShowcaseSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        ShowcaseManagerResponses responses = managerService.getMemberShowcaseInfo(1L);

        assertThat(responses).isNotNull();
    }


    @Test
    @DisplayName("관리자 사용자 그룹 정보 조회 성공")
    public void getEventToastSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        EventToastDataManagerResponses responses = managerService.getMemberEventToastInfo(1L);

        assertThat(responses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 그룹 정보 조회 성공")
    public void getGiftToastSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        GiftToastDataManagerResponses responses = managerService.getMemberGiftToastInfo(1L);

        assertThat(responses).isNotNull();
    }



    @Test
    @DisplayName("관리자 사용자 그룹 정보 조회 성공")
    public void getPaymentSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        PaymentManagerResponses responses = managerService.getMemberPaymentManagerInfo(1L);

        assertThat(responses).isNotNull();
    }

    @Test
    @DisplayName("결제 아이템 관련 정보 생성 실패")
    public void createItemDataFail(){
        ItemType itemType = mock(ItemType.class);
        long itemId = 1L;

        NullPointerException exception = assertThrows(NullPointerException.class, () -> managerService.createItemData(itemType, itemId));
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("결제 아이템 관련 정보 성공")
    public void createItemDataSuccess(){
        ReflectionTestUtils.setField(icon, "id", 1L);
        ItemType itemType = mock(ItemType.class);
        long itemId = 1L;

        when(iconGroupRepository.getById(anyLong())).thenReturn(iconGroup);

        MemberItemDataResponse memberItemDataResponse = managerService.createItemData(itemType, itemId);
        assertThat(memberItemDataResponse).isNotNull();
    }

    @Test
    @DisplayName("관리자 전체 유저와 제작자 수 조회")
    public void getMemberSignUpInfo(){
        //given
        List<Member> users = List.of(setUpMember());
        List<Member> creators = setUpCreators();

        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(users);
        when(memberRepository.findAllByMemberRole(MemberRole.CREATOR)).thenReturn(creators);

        //when
        MemberSignUpInfo memberSignUpInfo = managerService.getMemberSignUpInfo();

        //then
        Assertions.assertEquals(users.size(), memberSignUpInfo.totalUserCount());
        Assertions.assertEquals(creators.size(), memberSignUpInfo.totalCreatorCount());

    }

}
