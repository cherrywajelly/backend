package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberItemDataResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
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

    @InjectMocks
    private ManagerServiceImpl managerService;

    private Member member;
    private Premium premium;
    private Team team;
    private Icon icon;
    private IconGroup iconGroup;
    private Follow follow;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        String name = "name";

        member = Member.builder().memberRole(MemberRole.USER).build();
        premium = Premium.builder().build();
        team = Team.builder().build();
        icon = Icon.builder().build();
        iconGroup = IconGroup.builder().name(name).build();
        follow = Follow.builder().followerId(1L).build();
    }

    @Test
    @DisplayName("관리자 사용자 목록 조회")
    public void getMembersForManager(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.findAllByMemberRole(MemberRole.USER)).thenReturn(List.of(member));

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

        NullPointerException exception = assertThrows(NullPointerException.class, () -> managerService.createItemData(itemType, itemId));
        assertThat(exception).isNotNull();
    }

}