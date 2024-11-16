package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.BasicImage.BASIC_PROFILE_IMAGE_URL;


@Service
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final IconGroupRepository iconGroupRepository;
    private final IconMemberRepository iconMemberRepository;
    private final PremiumRepository premiumRepository;

    public LoginServiceImpl(final JwtService jwtService, final MemberRepository memberRepository, final PremiumRepository premiumRepository,
                            final IconGroupRepository iconGroupRepository, final IconMemberRepository iconMemberRepository) {
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
        this.iconGroupRepository = iconGroupRepository;
        this.iconMemberRepository = iconMemberRepository;
        this.premiumRepository = premiumRepository;
    }


    @Transactional
    @Override
    public LoginResponse loginToService(final String email, final LoginType loginType, final MemberRole memberRole) {

        Optional<Member> findMember = memberRepository.findByEmail(email);

        if(findMember.isPresent()){
            return jwtService.createJwts(LoginMember.from(findMember.get()), false);
        }

        String nickname = RandomStringUtils.randomAlphabetic(7);
        Member member = memberRepository.save(
                Member.builder()
                        .email(email)
                        .nickname(nickname)
                        .memberProfileUrl(BASIC_PROFILE_IMAGE_URL)
                        .premiumId(premiumRepository.getByPremiumType(PremiumType.BASIC).getId())
                        .loginType(loginType)
                        .memberRole(memberRole)
                        .build()
        );
        addBuiltinIcon(member);
        return jwtService.createJwts(LoginMember.from(member), true);

    }


    @Transactional
    @Override
    public void addBuiltinIcon(final Member member) {
        List<IconGroup> iconGroups = iconGroupRepository.findAllByIconBuiltin(IconBuiltin.BUILTIN);
        for (IconGroup iconGroup : iconGroups) {
            iconMemberRepository.save(IconMember.builder()
                    .memberId(member.getId())
                    .iconGroupId(iconGroup.getId())
                    .build());
        }
    }

}
