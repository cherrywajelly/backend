package com.timeToast.timeToast.repository.gift_toast.gift_toast_owner;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastOwnerResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceMember;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.QGiftToastOwner.giftToastOwner;
import static com.timeToast.timeToast.domain.member.member.QMember.member;

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
    public List<ToastPieceMember> findToastPieceMemberByGiftToastId(final long giftToastId) {
        return queryFactory
                .select(Projections.constructor(ToastPieceMember.class, member.id, member.nickname, member.memberProfileUrl))
                .from(member)
                .where(member.id.in(
                        JPAExpressions.select(giftToastOwner.memberId)
                                .from(giftToastOwner)
                                .where(giftToastOwner.giftToastId.eq(giftToastId), giftToastOwner.isVisible.isTrue())
                ))
                .fetch();
    }

    @Override
    public List<GiftToastOwnerResponse> findAllGiftToastMemberByGiftToastId(final long giftToastId){
        return queryFactory
                .select(Projections.constructor(GiftToastOwnerResponse.class, member.id, member.nickname))
                .from(member)
                .rightJoin(giftToastOwner)
                .on(giftToastOwner.memberId.eq(member.id))
                .where(giftToastOwner.id.eq(giftToastId),
                         giftToastOwner.isVisible.isTrue())
                .fetch();
    }

    @Override
    public void delete(final GiftToastOwner giftToastOwner) {
        giftToastOwnerJpaRepository.delete(giftToastOwner);
    }
}