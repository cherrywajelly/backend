package com.timeToast.timeToast.repository.member.member;

import com.timeToast.timeToast.domain.member.member.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(final Member member);
    Member getById(final long memberId);
    Optional<Member> findById(final long memberId);
    Optional<Member> findByEmail(final String email);
    boolean existsByNickname(final String nickname);
    void delete(final Member member);
}
