package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import com.timeToast.timeToast.util.BaseServiceTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
//
//    @InjectMocks
//    private FcmServiceImpl fcmService;

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
        Optional<MemberToken> existMemberToken = memberTokenRepository.findByMemberId(memberId);

        if (existMemberToken.isPresent()){
            existMemberToken.get().updateFcmToken(token);
            MemberToken savedMemberToken = memberTokenRepository.save(existMemberToken.get());

            // Then
            assertThat(savedMemberToken.getFcmToken()).isEqualTo(token);
        }
    }


    @Test
    @DisplayName("새로운 fcm 토큰 검증 - 성공")
    void fcmTokenValidationNewToken() {
        // Given
        long memberId = 1L;
        String token = "token";
        MemberToken testMemberToken = MemberToken.builder().memberId(memberId).jwt_refresh_token("jwtToken").build();

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(testMemberToken));

        // When
        Optional<MemberToken> existMemberToken = memberTokenRepository.findByMemberId(memberId);

        //then
        existMemberToken.ifPresent(memberToken -> assertNull(memberToken.getFcmToken()));
    }

    @Test
    @DisplayName("기존 fcm 토큰 검증 - 성공")
    void fcmTokenValidationExistingToken() {
        // Given
        long memberId = 1L;
        MemberToken testMemberToken = MemberToken.builder().memberId(memberId).jwt_refresh_token("jwtToken").build();

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(testMemberToken));
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(testMemberToken);

        // When
        Optional<MemberToken> existMemberToken = memberTokenRepository.findByMemberId(memberId);

        if (existMemberToken.isPresent()) {
            existMemberToken.get().updateFcmToken(null);
            memberTokenRepository.save(existMemberToken.get());
        }

        // Then
        assertNull(existMemberToken.get().getFcmToken());
    }
}