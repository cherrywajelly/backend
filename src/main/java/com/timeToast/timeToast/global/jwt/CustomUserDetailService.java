package com.timeToast.timeToast.global.jwt;

import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.getById(Integer.parseInt(username));

        return User.builder()
                .username(member.getId().toString())
                .password("password")
                .roles(member.getMemberRole().toString())
                .build();

    }


}
