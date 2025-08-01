package com.timeToast.timeToast.repository.jpa.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
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
    List<Member> findAllByMemberRole(final MemberRole memberRole);
    void delete(final Member member);
    void deleteById(final long memberId);
    Optional<Member> findByFcmToken(final String token);
}
