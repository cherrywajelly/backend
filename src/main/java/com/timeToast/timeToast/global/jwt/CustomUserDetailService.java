package com.timeToast.timeToast.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.global.exception.InternalServerException;
import com.timeToast.timeToast.global.exception.UnauthorizedException;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Component
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final ObjectMapper objectMapper;

    public CustomUserDetailService(final MemberRepository memberRepository, final MemberTokenRepository memberTokenRepository,
                                   final ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.memberTokenRepository = memberTokenRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            //parameter username = loginMember
            LoginMember loginMember =  objectMapper.readValue(username, LoginMember.class);

            Member member = memberRepository.getById(loginMember.id());

            return CustomUserDetails.builder()
                    .member(member)
                    .build();

        } catch (JsonProcessingException e) {
            throw new InternalServerException(LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR.getMessage());
        }




    }


}
