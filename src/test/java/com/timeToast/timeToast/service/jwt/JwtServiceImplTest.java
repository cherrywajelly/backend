package com.timeToast.timeToast.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.global.constant.JwtKey;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import com.timeToast.timeToast.service.member_token.MemberTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @Mock
    private MemberTokenService memberJwtRefreshTokenService;

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

    @Test
    @DisplayName("새로운 유저의 jwt 토큰 생성")
    public void createJwts(){
        //given
        LoginMember loginMember = loginMemberSetup();
        ReflectionTestUtils.setField(jwtKey, "JWT_KEY", "uYk/J8WlWFI+RukF+sEq6HZT98lOozvW3Z8lcIvlkBY=");

        //when
        LoginResponse loginResponse = jwtService.createJwts(loginMember, true);

        //then
        assertNotNull(loginResponse);
        assertEquals(true, loginResponse.isNew());
    }

//    @Test
//    @DisplayName("refresh token 갱신")
//    public void tokenRenewal() throws JsonProcessingException {
//        //given
//        LoginMember loginMember = loginMemberSetup();
//        ReflectionTestUtils.setField(jwtKey, "JWT_KEY", "uYk/J8WlWFI+RukF+sEq6HZT98lOozvW3Z8lcIvlkBY=");
//
//        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
//        when(objectMapper.readValue(anyString(), eq(LoginMember.class))).thenReturn(loginMember);
//        String refrehToken = "refreshToken";
//        //when
//        LoginResponse loginResponse = jwtService.tokenRenewal(refrehToken);
//
//        //then
//        assertNotNull(loginResponse);
//        assertEquals(false, loginResponse.isNew());
//    }

}