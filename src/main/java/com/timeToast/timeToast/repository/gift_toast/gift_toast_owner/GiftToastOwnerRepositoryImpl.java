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
import static com.timeToast.timeToast.domain.toast_piece.toast_piece.QToastPiece.toastPiece;

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
    public List<GiftToastOwner> findAllByGiftToastId(final long giftToastId) {
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
                                .where(giftToastOwner.giftToastId.eq(giftToastId))
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
                .where(giftToastOwner.id.eq(giftToastId))
                .fetch();
    }

    @Override
    public boolean checkAllGiftToastOwnerWrote(final long giftToastId){

        List<GiftToastOwner> giftToastOwners =
                queryFactory
                        .select(giftToastOwner)
                        .from(giftToastOwner)
                        .where(giftToastOwner.giftToastId.eq(giftToastId),
                                giftToastOwner.memberId.in(
                                        JPAExpressions
                                                .select(toastPiece.memberId)
                                                .from(toastPiece)
                                                .where(toastPiece.giftToastId.eq(giftToastId))))
                        .fetch();

        if(giftToastOwners.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void deleteByMemberIdAndGiftToastId(final long memberId, final long giftToastId) {
        giftToastOwnerJpaRepository.deleteAllByMemberIdAndGiftToastId(memberId,giftToastId);
    }

    @Override
    public void delete(final GiftToastOwner giftToastOwner) {
        giftToastOwnerJpaRepository.delete(giftToastOwner);
    }
}
