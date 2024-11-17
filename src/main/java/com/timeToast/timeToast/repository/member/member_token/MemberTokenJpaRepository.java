package com.timeToast.timeToast.repository.member.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberTokenJpaRepository extends JpaRepository<MemberToken, Long> {

    Optional<MemberToken> findByMemberId(final long memberId);
    void deleteByMemberId(final long memberId);
    Optional<MemberToken> findByFcmToken(final String token);

}
