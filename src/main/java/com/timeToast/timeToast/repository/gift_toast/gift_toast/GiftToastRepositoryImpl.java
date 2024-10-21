package com.timeToast.timeToast.repository.gift_toast.gift_toast;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.domain.gift_toast.gift_toast.QGiftToast.giftToast;
import static com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.QGiftToastOwner.giftToastOwner;

@Repository
public class GiftToastRepositoryImpl implements GiftToastRepository{

    private final GiftToastJpaRepository giftToastJpaRepository;
    private final JPAQueryFactory queryFactory;

    public GiftToastRepositoryImpl(final GiftToastJpaRepository giftToastJpaRepository, final JPAQueryFactory queryFactory) {
        this.giftToastJpaRepository = giftToastJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public GiftToast save(final GiftToast giftToast) {
        return giftToastJpaRepository.save(giftToast);
    }

    @Override
    public List<GiftToast> getGiftToastByMemberId(long memberId) {
        return queryFactory.select(
                giftToast
                )
                .from(giftToast)
                .join(giftToastOwner).on(giftToastOwner.giftToastId.eq(giftToast.id))
                .where(giftToastOwner.memberId.eq(memberId)).fetch();
    }
}
