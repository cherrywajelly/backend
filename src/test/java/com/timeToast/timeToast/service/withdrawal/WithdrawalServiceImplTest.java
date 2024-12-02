package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.team.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WithdrawalServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private GiftToastService giftToastService;

    @Mock
    private EventToastService eventToastService;

    @Mock
    private ShowcaseRepository showcaseRepository;

    @Mock
    private FcmRepository fcmRepository;

    @InjectMocks
    private WithdrawalServiceImpl withdrawalService;

    @Test
    @DisplayName("유저 탈퇴")
    public void memberWithdrawal(){
        //given
        //when
        Response response = withdrawalService.memberWithdrawal(1L);
        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("제작자 탈퇴")
    public void creatorWithdrawal(){
        //given

        //when
        Response response = withdrawalService.creatorWithdrawal(1L);
        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("관리자 탈퇴")
    public void adminWithdrawal(){
        //given

        //when
        Response response = withdrawalService.adminWithdrawal(1L);
        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }


}