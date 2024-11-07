package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.icon_member.MemberIconRepository;
import com.timeToast.timeToast.service.jwt.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberIconRepository memberIconRepository;

    public LoginServiceImpl(final JwtService jwtService, final MemberRepository memberRepository,
                            final IconGroupRepository iconGroupRepository, final MemberIconRepository memberIconRepository) {
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
        this.iconGroupRepository = iconGroupRepository;
        this.memberIconRepository = memberIconRepository;
    }


    @Transactional
    @Override
    public LoginResponse loginToService(String email, LoginType loginType) {

        Optional<Member> findMember = memberRepository.findByEmail(email);

        if(findMember.isPresent()){

            return jwtService.createJwts(LoginMember.from(findMember.get()), false);

        }else{
            Member member = memberRepository.save(
                    Member.builder()
                            .email(email)
                            .loginType(loginType)
                            .memberRole(MemberRole.USER)
                            .is_delete(false)
                            .build()
            );
            addBuiltInIconTest(member);
            return jwtService.createJwts(LoginMember.from(member), true);
        }
    }


    @Transactional
    @Override
    public void addBuiltInIconTest(Member member) {
        List<IconGroup> iconGroups = iconGroupRepository.findByIconBuiltin(IconBuiltin.BUILTIN);
        for (IconGroup iconGroup : iconGroups) {
            memberIconRepository.save(IconMember.builder()
                    .memberId(member.getId())
                    .iconGroupId(iconGroup.getId())
                    .build());
        }
    }

}
