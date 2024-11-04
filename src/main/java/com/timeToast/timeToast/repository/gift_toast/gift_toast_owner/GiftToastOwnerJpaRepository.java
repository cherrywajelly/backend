package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftToastOwnerJpaRepository extends JpaRepository<GiftToastOwner, Long> {

    List<GiftToastOwner> findAllByGiftToastId(final long giftToastId);
    void deleteAllByMemberIdAndGiftToastId(final long memberId, final long giftToastId);
}
