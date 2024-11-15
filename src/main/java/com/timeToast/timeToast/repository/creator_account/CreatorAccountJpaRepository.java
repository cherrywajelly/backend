package com.timeToast.timeToast.repository.creator_account;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorAccountJpaRepository extends JpaRepository<CreatorAccount, Long> {

    Optional<CreatorAccount> findByMemberId(final long memberId);

}
