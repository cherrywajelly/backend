package com.timeToast.timeToast.global.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

//    private final MemberRepository memberRepository;
//
//    public CustomUserDetailService(final MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
//        return memberRepository.findByLoginId(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    }


}
