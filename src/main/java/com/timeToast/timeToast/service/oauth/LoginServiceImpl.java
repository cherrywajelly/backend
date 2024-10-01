package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import com.timeToast.timeToast.service.oauth.KakaoLoginServiceImpl;
import com.timeToast.timeToast.service.oauth.LoginService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final KakaoLoginServiceImpl kakaoLoginServiceImpl;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    // for login test
    public String loadToKakaoLogin() {
        return kakaoLoginServiceImpl.loadToLogin();
    }

    public LoginResponse getAccessToken(String social, String code) {
        if (social.equals("kakao")){
            //TODO 일단 타입만 맞춰놓음. 로직에 따라 수정 바람.
            return loginToService(kakaoLoginServiceImpl.getKakaoAccessToken(code), LoginType.KAKAO);
        }
        else {
            //TODO 일단 타입만 맞춰놓음. 로직에 따라 수정 바람.
            return new LoginResponse("accessToken", "refreshToken");
        }
    }

    public LoginResponse loginToService(String email, LoginType loginType) {

        Optional<Member> findMember = memberRepository.findByEmail(email);
        Member member;

        if(findMember.isPresent()){
            member = findMember.get();
        }else{
            member = memberRepository.save(
                    Member.builder()
                            .email(email)
                            .loginType(LoginType.KAKAO)
                            .memberRole(MemberRole.ROLE_USER)
                            .is_delete(false)
                            .build()
            );
        }



        return new LoginResponse("accessToken","refreshToken");
    }
}
