package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import org.springframework.stereotype.Repository;

@Repository
public class GiftToastOwnerRepositoryImpl implements GiftToastOwnerRepository{

    private final GiftToastOwnerJpaRepository giftToastOwnerJpaRepository;

    public GiftToastOwnerRepositoryImpl(GiftToastOwnerJpaRepository giftToastOwnerJpaRepository) {
        this.giftToastOwnerJpaRepository = giftToastOwnerJpaRepository;
    }

    @Override
    public GiftToastOwner save(final GiftToastOwner giftToastOwner) {
        return giftToastOwnerJpaRepository.save(giftToastOwner);
    }
}
