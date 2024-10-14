package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    public ResponseEntity<String> postNickname(String nickname, long userId){

        Member member = memberRepository.getById(userId);

        // 이메일 중복 검증 로직
        boolean exist = memberRepository.existsByNickname(nickname);

        if (!exist) {
            member.updateNickname(nickname);
            return ResponseEntity.ok().body("닉네임이 등록되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다. 다시 입력해주세요.");
        }
    }


}
