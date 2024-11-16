package com.timeToast.timeToast.repository.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    Optional<Member> findByNickname(final String nickname);
    List<Member> findAllByMemberRole(final MemberRole memberRole);
    boolean existsByNickname(final String nickname);
}
