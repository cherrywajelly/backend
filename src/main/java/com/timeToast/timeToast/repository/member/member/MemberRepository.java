package com.timeToast.timeToast.repository.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(final Member member);
    Member getById(final long memberId);
    Optional<Member> findById(final long memberId);
    Optional<Member> findByEmail(final String email);
    List<Member> findMemberByNickname(final String nickname, final Pageable pageable);
    boolean existsByNickname(final String nickname);
    void delete(final Member member);
    void deleteById(final long memberId);
}
