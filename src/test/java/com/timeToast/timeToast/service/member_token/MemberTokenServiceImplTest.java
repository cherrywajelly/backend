package com.timeToast.timeToast.service.member_token;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberTokenServiceImplTest {

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @InjectMocks
    private MemberTokenServiceImpl memberTokenService;


    private MemberToken setUpMemberToken() {
        return MemberToken.builder()
                .memberId(1L)
                .jwt_refresh_token("jwt_refresh_token")
                .build();
    }
    @Test
    @DisplayName("member token 저장")
    public void save(){
        //given
        MemberToken memberToken = setUpMemberToken();
        when(memberTokenRepository.findByMemberId(any(Long.class))).thenReturn(Optional.of(memberToken));

        //when
        MemberToken memberToken1 = memberTokenService.save(1L,"refresh_token");

        //then
        assertNotNull(memberToken1);
    }

}