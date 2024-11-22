package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PremiumRepository premiumRepository;

    @Mock
    JwtService jwtService;

    @InjectMocks
    LoginServiceImpl loginService;

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

    private Premium setPremium() {
        return Premium.builder()
                .premiumType(PremiumType.BASIC)
                .count(0)
                .price(0)
                .build();
    }


    @Test
    @DisplayName("로그인 테스트")
    public void loginToService() {

        //given
        Member member = setUpMember();
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        Premium premium = setPremium();
        when(premiumRepository.getByPremiumType(any())).thenReturn(premium);

        when(jwtService.createJwts(any(LoginMember.class), any(Boolean.class))).thenReturn(new LoginResponse("accessToken","refreshToken", true));

        //when
        loginService.loginToService(member.getEmail(), member.getLoginType(), member.getMemberRole());

        //then
        verify(memberRepository, times(1)).save(any(Member.class));

    }


}
