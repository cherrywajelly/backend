package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberAdminResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberItemDataResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberSummaryResponse;
import com.timeToast.timeToast.dto.team.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PremiumRepository premiumRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private ShowcaseRepository showcaseRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private GiftToastRepository giftToastRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;

    @Mock
    private IconRepository iconRepository;

    @InjectMocks
    private ManagerServiceImpl managerService;

    private Member member;
    private Premium premium;
    private Team team;
    private Icon icon;
    private IconGroup iconGroup;
    private Follow follow;
    private IconMember iconMember;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        String name = "name";

        member = Member.builder().memberRole(MemberRole.USER).premiumId(1L).build();
        premium = Premium.builder().premiumType(PremiumType.PREMIUM).build();
        team = Team.builder().build();
        icon = Icon.builder().build();
        iconGroup = IconGroup.builder().name(name).build();
        follow = Follow.builder().followerId(1L).build();
        iconMember = IconMember.builder().build();
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
                    .build();
            ReflectionTestUtils.setField(tempMember, "id", i);
            members.add(tempMember);
        }
        return members;
    }


    @Test
    @DisplayName("관리자 role staff로 변환")
    public void saveToStaff(){
        //given

        Member user = Member.builder()
                .memberRole(MemberRole.USER)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(memberRepository.getById(anyLong())).thenReturn(user);

        assertEquals(MemberRole.USER, user.getMemberRole());

        //when
        MemberAdminResponse memberAdminResponse = managerService.saveToStaff(user.getId());


        //then
        assertEquals(MemberRole.STAFF, user.getMemberRole());
        assertEquals(MemberRole.STAFF, memberAdminResponse.memberRole());
    }

    @Test
    @DisplayName("관리자 role creators로 변환")
    public void saveToCreators(){
        //given

        Member user = Member.builder()
                .memberRole(MemberRole.USER)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(memberRepository.getById(anyLong())).thenReturn(user);

        assertEquals(MemberRole.USER, user.getMemberRole());

        //when
        MemberAdminResponse memberAdminResponse = managerService.saveToCreators(user.getId());


        //then
        assertEquals(MemberRole.CREATOR, user.getMemberRole());
        assertEquals(MemberRole.CREATOR, memberAdminResponse.memberRole());
    }

    @Test
    @DisplayName("관리자 role user로 변환")
    public void saveToUser(){
        //given

        Member user = Member.builder()
                .memberRole(MemberRole.CREATOR)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);

        when(memberRepository.getById(anyLong())).thenReturn(user);

        assertEquals(MemberRole.CREATOR, user.getMemberRole());

        //when
        MemberAdminResponse memberAdminResponse = managerService.saveToUser(user.getId());


        //then
        assertEquals(MemberRole.USER, user.getMemberRole());
        assertEquals(MemberRole.USER, memberAdminResponse.memberRole());
    }






    @Test
    @DisplayName("관리자 사용자 목록 조회")
    public void getMembersForManager(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(member));
        ReflectionTestUtils.setField(premium, "id", 1L);
        when(premiumRepository.getById(anyLong())).thenReturn(premium);

        MemberManagerResponses memberManagerResponses = managerService.getMembersForManagers();

        assertThat(memberManagerResponses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 목록 조회 실패")
    public void getMembersForManagerFail(){
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(member));

        NullPointerException exception = assertThrows(NullPointerException.class, () -> managerService.getMembersForManagers());

        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 정보 조회 실패")
    public void getMemberInfoForManagerFail(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.getMemberInfoForManager(1L));
    }

    @Test
    @DisplayName("관리자 사용자 정보 조회 성공")
    public void getMemberInfoForManagerSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(member);

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.getMemberInfoForManager(1L));
    }

    @Test
    @DisplayName("관리자 사용자 팔로우 정보 조회 성공")
    public void getFollowSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        when(followRepository.findAllByFollowingId(1L)).thenReturn(List.of(follow));
        when(memberRepository.getById(anyLong())).thenReturn(member);

        FollowManagerResponses responses = managerService.getMemberFollowInfo(1L);

        assertThat(responses).isNotNull();
    }

    @Test
    @DisplayName("관리자 사용자 팔로잉 정보 조회 성공")
    public void getFollowingSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        when(followRepository.findAllByFollowerId(1L)).thenReturn(List.of(follow));
        when(memberRepository.getById(anyLong())).thenReturn(member);
        FollowingManagerResponses responses = managerService.getMemberFollowingInfo(1L);

        assertThat(responses).isNotNull();
    }

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
    public void getIconGroupSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(iconMemberRepository.findByMemberId(anyLong())).thenReturn(List.of(iconMember));
        when(iconRepository.findAllByIconGroupId(anyLong())).thenReturn(List.of(icon));
        when(iconGroupRepository.getById(anyLong())).thenReturn(iconGroup);

        IconGroupManagerResponses responses = managerService.getMemberIconGroupInfo(1L);

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
    public void getMembersCountForManagers(){
        //given
        List<Member> users = List.of(setUpMember());
        List<Member> creators = setUpCreators();

        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(users);
        when(memberRepository.findAllByMemberRole(MemberRole.CREATOR)).thenReturn(creators);

        //when
        MemberSummaryResponse memberSummaryResponse = managerService.getMembersCountForManagers();

        //then
        Assertions.assertEquals(users.size(), memberSummaryResponse.totalUserCount());
        Assertions.assertEquals(creators.size(), memberSummaryResponse.totalCreatorCount());

    }

}
