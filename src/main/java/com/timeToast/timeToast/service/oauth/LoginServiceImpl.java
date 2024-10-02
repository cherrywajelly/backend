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

    public LoginResponse getAccessToken(LoginType social, String code) {
        if (social.equals(LoginType.KAKAO)){
            return loginToService(kakaoLoginServiceImpl.getKakaoAccessToken(code), LoginType.KAKAO);
        }
        else {
            //TODO google social login.
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
                            .loginType(loginType)
                            .memberRole(MemberRole.ROLE_USER)
                            .is_delete(false)
                            .build()
            );
        }

        return jwtService.createJwts(member.getId());
    }
}
