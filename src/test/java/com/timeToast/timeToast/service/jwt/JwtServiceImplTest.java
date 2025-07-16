package com.timeToast.timeToast.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.global.constant.JwtKey;
import com.timeToast.timeToast.global.exception.UnauthorizedException;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import com.timeToast.timeToast.repository.redis.member_token.MemberTokenRepository;
import com.timeToast.timeToast.service.redis.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private JwtKey jwtKey;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private JwtServiceImpl jwtService;

    private LoginMember loginMemberSetup(){
        return LoginMember.builder()
                .email("test@gmail.com")
                .id(1L)
                .role(MemberRole.USER)
                .build();
    }

    private MemberToken memberTokenSetup(){
        return MemberToken.builder()
                .memberId(1L)
                .jwt_refresh_token("uYk/J8WlWFI+RukF+sEq6HZT98lOozvW3Z8lcIvlkBY=")
                .build();
    }

    @Test
    @DisplayName("새로운 유저의 jwt 토큰 생성")
    public void createJwts(){
        //given
        LoginMember loginMember = loginMemberSetup();
        ReflectionTestUtils.setField(jwtKey, "JWT_KEY", "uYk/J8WlWFI+RukF+sEq6HZT98lOozvW3Z8lcIvlkBY=");

        MemberToken memberToken = memberTokenSetup();
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(memberToken);

        //when
        LoginResponse loginResponse = jwtService.createJwts(loginMember, true);

        //then
        assertNotNull(loginResponse);
        assertTrue(loginResponse.isNew());
    }

    @Test
    @DisplayName("refresh token 갱신 실패: 만료된 토큰")
    public void tokenRenewal(){
        //given
        ReflectionTestUtils.setField(jwtKey, "JWT_KEY", "uYk/J8WlWFI+RukF+sEq6HZT98lOozvW3Z8lcIvlkBY=");

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(false);

        String refreshToken = "refreshToken";

        //when
        //then
        assertThrows( UnauthorizedException.class, () -> jwtService.tokenRenewal(refreshToken));
    }


}