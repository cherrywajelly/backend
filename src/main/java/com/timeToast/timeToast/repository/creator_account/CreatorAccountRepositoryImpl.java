package com.timeToast.timeToast.repository.creator_account;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CreatorAccountRepositoryImpl implements CreatorAccountRepository {

    private final CreatorAccountJpaRepository creatorAccountJpaRepository;

    public CreatorAccountRepositoryImpl(final CreatorAccountJpaRepository creatorAccountJpaRepository) {
        this.creatorAccountJpaRepository = creatorAccountJpaRepository;
    }

    @Override
    public CreatorAccount save(final CreatorAccount creatorAccount) {
        return creatorAccountJpaRepository.save(creatorAccount);
    }

    @Override
    public Optional<CreatorAccount> findByMemberId(final long memberId) {
        return creatorAccountJpaRepository.findByMemberId(memberId);
    }
}
