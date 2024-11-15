package com.timeToast.timeToast.repository.creator_account;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;

public class CreatorAccountRepositoryImpl implements CreatorAccountRepository {

    private final CreatorAccountJpaRepository creatorAccountJpaRepository;

    public CreatorAccountRepositoryImpl(final CreatorAccountJpaRepository creatorAccountJpaRepository) {
        this.creatorAccountJpaRepository = creatorAccountJpaRepository;
    }

    @Override
    public CreatorAccount save(CreatorAccount creatorAccount) {
        return creatorAccountJpaRepository.save(creatorAccount);
    }
}
