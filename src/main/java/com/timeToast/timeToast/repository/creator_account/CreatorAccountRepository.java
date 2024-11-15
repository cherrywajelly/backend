package com.timeToast.timeToast.repository.creator_account;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;

import java.util.Optional;

public interface CreatorAccountRepository {
    CreatorAccount save(final CreatorAccount creatorAccount);
    Optional<CreatorAccount> findByMemberId(final long memberId);
}
