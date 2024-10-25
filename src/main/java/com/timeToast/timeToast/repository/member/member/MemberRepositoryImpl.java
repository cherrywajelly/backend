package com.timeToast.timeToast.repository.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_EXISTS;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }


    @Override
    public Member getById(final long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new BadRequestException(MEMBER_NOT_EXISTS.getMessage()));
    }

    @Override
    public Optional<Member> findById(final long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void delete(final Member member) {
        memberJpaRepository.delete(member);
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) { return memberJpaRepository.findByNickname(nickname); }

    @Override
    public boolean existsByNickname(final String nickname) { return memberJpaRepository.existsByNickname(nickname);}
}
