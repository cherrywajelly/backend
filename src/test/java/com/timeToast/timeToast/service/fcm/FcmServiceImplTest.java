package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FcmServiceImplTest {

//    @Mock
//    private FcmRepository fcmRepository;

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @InjectMocks
    private FcmServiceImpl fcmService;

    @Test
    @DisplayName("fcm 토큰 저장 - 성공")
    void saveToken() {
        // Given
        long memberId = 1L;
        String token = "token";
        MemberToken testMemberToken = MemberToken.builder().memberId(memberId).jwt_refresh_token("jwtToken").build();

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(testMemberToken));
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(testMemberToken);

        // When
        Response response = fcmService.saveToken(memberId, token);

        // Then
        assertThat(testMemberToken.getFcmToken()).isEqualTo(token);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }


    @Test
    @DisplayName("기존 fcm 토큰 검증 - 성공")
    void fcmTokenValidationNewToken() {
        // Given
        long memberId = 1L;
        String token = "token";
        MemberToken testMemberToken = MemberToken.builder().memberId(2L).jwt_refresh_token("jwtToken").build();
        testMemberToken.updateFcmToken(token);

        when(memberTokenRepository.findByFcmToken(token)).thenReturn(Optional.of(testMemberToken));
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(testMemberToken);

        // When
        fcmService.fcmTokenValidation(memberId, token);

        //then
        assertNull(testMemberToken.getFcmToken());
    }

    @Test
    @DisplayName("새 fcm 토큰 검증 - 성공")
    void fcmTokenValidationExistingToken() {
        // Given
        long memberId = 1L;
        String token = "token";
        MemberToken testMemberToken = MemberToken.builder().memberId(memberId).jwt_refresh_token("jwtToken").build();

        when(memberTokenRepository.findByFcmToken(token)).thenReturn(Optional.of(testMemberToken));

        // When
        fcmService.fcmTokenValidation(memberId, token);

        // Then
        assertNull(testMemberToken.getFcmToken());
    }
}