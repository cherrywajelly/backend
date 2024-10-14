package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final KakaoLoginImpl kakaoLoginServiceImpl;
    private final GoogleLoginImpl googleLoginImpl;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    // for login test
    public String loadToKakaoLogin() {
        return kakaoLoginServiceImpl.loadToLogin();
    }
    public String loadToGoogleLogin() {
        return googleLoginImpl.loadToLogin();
    }

    public LoginResponse getAccessToken(LoginType social, String code) {
        if (social.equals(LoginType.KAKAO)){
            return loginToService(kakaoLoginServiceImpl.getAccessToken(code), LoginType.KAKAO);
        }
        else {
            return loginToService(googleLoginImpl.getAccessToken(code), LoginType.GOOGLE);
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
                            .loginType(loginType)
                            .memberRole(MemberRole.USER)
                            .is_delete(false)
                            .build()
            );
        }

        return jwtService.createJwts(LoginMember.from(member));
    }

}
