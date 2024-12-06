package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PremiumRepository premiumRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private ManagerServiceImpl managerService;

    private Member member;
    private Premium premium;

    @BeforeEach
    void setUp() {
        long memberId = 1L;

        member = Member.builder().memberRole(MemberRole.USER).build();
        premium = Premium.builder().build();
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
    @DisplayName("관리자 사용자 정보 조회 실패")
    public void getMemberInfoForManagerFail(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.getMemberInfoForManager(1L));
    }

    @Test
    @DisplayName("관리자 사용자 정보 조회 실패")
    public void getMemberInfoForManagerSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(anyLong())).thenReturn(member);

        NullPointerException exception = assertThrows(NullPointerException.class, ()-> managerService.getMemberInfoForManager(1L));
    }

    @Test
    @DisplayName("관리자 사용자 팔로우 정보 조회 성공")
    public void getFollowSuccess(){
        ReflectionTestUtils.setField(member, "id", 1L);

        FollowManagerResponses responses = managerService.getMemberFollowInfo(1L);

        assertThat(responses).isNotNull();
    }
}
