package com.timeToast.timeToast.repository.member;

import com.timeToast.timeToast.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    Optional<Member> findByNickname(final String nickname);

    boolean existsByNickname(final String nickname);
}
