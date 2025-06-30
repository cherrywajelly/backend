package com.timeToast.timeToast.repository.redis.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberTokenRedisRepository extends CrudRepository<MemberToken, Long> {

    Optional<MemberToken> findByMemberId(final long memberId);
    void deleteByMemberId(final long memberId);

}
