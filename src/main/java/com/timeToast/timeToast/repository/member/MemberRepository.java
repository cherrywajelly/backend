package com.timeToast.timeToast.repository.member;

import com.timeToast.timeToast.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {

    Member getById(final long memberId);
    Optional<Member> findById(final long memberId);
    Optional<Member> findByEmail(final String email);
    Member save(final Member member);
    void delete(final Member member);
    Optional<Member> findByNickname(final String nickname);

    boolean existsByNickname(final String nickname);
}
