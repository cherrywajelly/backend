package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return loginToService(kakaoLoginServiceImpl.getKakaoAccessToken(code), LoginType.KAKAO);
        }
        else {
            return loginToService(googleLoginImpl.getGoogleAccessToken(code), LoginType.GOOGLE);
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

    public ResponseEntity<String> postNickname(String nickname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getCredentials(); //email

        //이메일로 유저 찾기
        Member member = memberRepository.findByEmail(email).orElseThrow();

        // 이메일 중복 검증 로직
        Member existName = memberRepository.existsByNickname(nickname).orElseThrow();

        if (existName.getNickname() == null) {
            member.updateNickname(nickname);
            memberRepository.save(member);
            return ResponseEntity.ok().body("닉네임이 등록되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다. 다시 입력해주세요.");
        }
    }
}
