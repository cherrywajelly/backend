package com.timeToast.timeToast.repository.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    Optional<Member> findByNickname(final String nickname);
    boolean existsByNickname(final String nickname);
}
