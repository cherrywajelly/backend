package com.timeToast.timeToast.repository;

import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.global.custom_exception.NotFoundException;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }


    @Override
    public Member getById(final long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND.getMessage()));
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void delete(final Member member) {
        memberJpaRepository.delete(member);
    }
}
