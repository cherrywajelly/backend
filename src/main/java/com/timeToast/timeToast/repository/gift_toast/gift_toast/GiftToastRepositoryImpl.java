package com.timeToast.timeToast.repository.gift_toast.gift_toast;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.domain.gift_toast.gift_toast.QGiftToast.giftToast;
import static com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.QGiftToastOwner.giftToastOwner;
import static com.timeToast.timeToast.domain.toast_piece.toast_piece.QToastPiece.toastPiece;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.GIFT_TOAST_NOT_FOUND;

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
    public Optional<GiftToast> findByGiftToastId(final long giftToastId) {
        return giftToastJpaRepository.findById(giftToastId);
    }

    @Override
    public List<GiftToast> findAllGiftToastsByMemberId(final long memberId) {

        return queryFactory
                .select(giftToast)
                .from(giftToast)
                .join(giftToastOwner)
                .on(giftToastOwner.giftToastId.eq(giftToast.id), giftToastOwner.memberId.eq(memberId))
                .where(giftToastOwner.isNotNull())
                .fetch();
    }

    @Override
    public List<GiftToast> findAllGiftToastsByMemberIdAndNotOpen(final long memberId) {

        return queryFactory
                .select(giftToast)
                .from(giftToast)
                .join(giftToastOwner)
                .on(giftToastOwner.giftToastId.eq(giftToast.id), giftToastOwner.memberId.eq(memberId))
                .where(giftToastOwner.isNotNull(), giftToast.isOpened.isFalse())
                .fetch();
    }

    @Override
    public List<GiftToast> findAllGiftToastToOpen() {

        return queryFactory
                .selectFrom(giftToast)
                .where(giftToast.isOpened.isFalse(),
                        giftToast.openedDate.before(LocalDate.now()).or(giftToast.openedDate.eq(LocalDate.now())))
                .fetch();
    }

    @Override
    public GiftToast getById(final long giftToastId) {
        return giftToastJpaRepository.findById(giftToastId).orElseThrow(() -> new NotFoundException(GIFT_TOAST_NOT_FOUND.getMessage()));
    }

    @Override
    public void deleteById(final long giftToastId) {
        giftToastJpaRepository.deleteById(giftToastId);
    }

}
