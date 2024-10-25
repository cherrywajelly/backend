package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.dto.member.member.LoginResponse;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member_icon.MemberIconRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

//    private final KakaoLoginImpl kakaoLoginServiceImpl;
//    private final GoogleLoginImpl googleLoginImpl;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberIconRepository memberIconRepository;

    // for login test
//    public String loadToKakaoLogin() {
//        return kakaoLoginServiceImpl.loadToLogin();
//    }
//    public String loadToGoogleLogin() {
//        return googleLoginImpl.loadToLogin();
//    }

//    public LoginResponse getAccessToken(LoginType social, String code) {
//        if (social.equals(LoginType.KAKAO)){
//            return loginToService(kakaoLoginServiceImpl.getAccessToken(code), LoginType.KAKAO);
//        }
//        else {
//            return loginToService(googleLoginImpl.getAccessToken(code), LoginType.GOOGLE);
//        }
//    }

    @Override
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


    // TODO db 연결 후 loginToService 랑 병합
    @Override
    public void addBuiltInIconTest(Member member) {
        List<IconGroup> iconGroups = iconGroupRepository.findByIconType(IconType.BUILTIN);
        for (IconGroup iconGroup : iconGroups) {
            memberIconRepository.save(IconMember.builder()
                    .memberId(member.getId())
                    .iconGroupId(iconGroup.getId())
                    .build());
        }
        System.out.println("기본 이미지가 등록되었습니다");
    }

}
