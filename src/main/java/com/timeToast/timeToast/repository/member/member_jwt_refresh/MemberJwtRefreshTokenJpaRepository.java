package com.timeToast.timeToast.repository.member.member_jwt_refresh;

import com.timeToast.timeToast.domain.member.member_jwt_refresh.MemberJwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJwtRefreshTokenJpaRepository extends JpaRepository<MemberJwtRefreshToken, Long> {

    Optional<MemberJwtRefreshToken> findByMemberId(final long memberId);

}
