package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastOwnerResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.QGiftToastOwner.giftToastOwner;
import static com.timeToast.timeToast.domain.gift_toast.toast_piece.QToastPiece.toastPiece;
import static com.timeToast.timeToast.domain.member.QMember.member;

@Repository
public class GiftToastOwnerRepositoryImpl implements GiftToastOwnerRepository{

    private final GiftToastOwnerJpaRepository giftToastOwnerJpaRepository;
    private final JPAQueryFactory queryFactory;

    public GiftToastOwnerRepositoryImpl(final GiftToastOwnerJpaRepository giftToastOwnerJpaRepository, final JPAQueryFactory queryFactory) {
        this.giftToastOwnerJpaRepository = giftToastOwnerJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public GiftToastOwner save(final GiftToastOwner giftToastOwner) {
        return giftToastOwnerJpaRepository.save(giftToastOwner);
    }

    @Override
    public List<GiftToastOwner> findByGiftToastId(final long giftToastId) {
        return giftToastOwnerJpaRepository.findAllByGiftToastId(giftToastId);
    }

    @Override
    public List<GiftToastOwnerResponse> findGiftToastOwnerResponsesByGiftToastId(final long giftToastId){

        BooleanExpression toastPieceExists = JPAExpressions
                .selectOne()
                .from(toastPiece)
                .where(toastPiece.memberId.eq(member.id))
                .exists();

         queryFactory
                .select(member.nickname, member.id,
                        toastPieceExists.when(true).then(true).otherwise(false))// 서브쿼리 결과를 이용해 true/false 반환
                .from(giftToastOwner)
                .join(member).on(giftToastOwner.memberId.eq(member.id)) // giftToastOwner와 member 테이블 조인
                .where(giftToastOwner.giftToastId.eq(giftToastId)) // giftToastId 조건
                .fetch();

        return null;
    }

    @Override
    public void delete(final GiftToastOwner giftToastOwner) {
        giftToastOwnerJpaRepository.delete(giftToastOwner);
    }
}
