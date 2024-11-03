package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.NICKNAME_CONFLICT;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    public void postNickname(String nickname, long userId){

        Member member = memberRepository.getById(userId);

        // 이메일 중복 검증 로직
        boolean exist = memberRepository.existsByNickname(nickname);

        if (!exist) {
            member.updateNickname(nickname);
        }
        else {
            new ConflictException(NICKNAME_CONFLICT.getMessage());;
        }
    }

    public void isNicknameAvailable(String nickname) {
        Optional<Member> findMember = memberRepository.findByNickname(nickname);

        if(findMember.isPresent()){
            new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
    }


}
