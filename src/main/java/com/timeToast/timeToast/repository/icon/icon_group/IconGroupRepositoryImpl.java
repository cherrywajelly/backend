package com.timeToast.timeToast.repository.icon.icon_group;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.enums.icon_group.*;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.response.IconGroupInfo;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.domain.icon.icon_group.QIconGroup.iconGroup;
import static com.timeToast.timeToast.domain.icon.icon_member.QIconMember.iconMember;
import static com.timeToast.timeToast.domain.member.member.QMember.member;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_GROUP_NOT_FOUND;


@Repository
public class IconGroupRepositoryImpl implements IconGroupRepository{

    private final IconGroupJpaRepository iconGroupJpaRepository;
    private final JPAQueryFactory queryFactory;

    public IconGroupRepositoryImpl(final IconGroupJpaRepository iconGroupJpaRepository,
                                   final JPAQueryFactory queryFactory) {
        this.iconGroupJpaRepository = iconGroupJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public IconGroup save(final IconGroup iconGroup) {
        return iconGroupJpaRepository.save(iconGroup);
    }

    @Override
    public IconGroup getById(final long iconGroupId) {
        return iconGroupJpaRepository.findById(iconGroupId)
                .orElseThrow(() -> new NotFoundException(ICON_GROUP_NOT_FOUND.getMessage()));
    }

    @Override
    public IconGroup getByIdAndMemberId(final long memberId, final long iconGroupId) {
        return iconGroupJpaRepository.findByIdAndMemberId(iconGroupId, memberId)
                .orElseThrow(()-> new NotFoundException(ICON_GROUP_NOT_FOUND.getMessage()));
    }

    @Override
    public IconGroupInfo getIconGroupInfo(final long memberId, final long iconGroupId){
        return queryFactory.select(
                    Projections.constructor(
                            IconGroupInfo.class,
                            iconGroup.id, iconGroup.name,
                            ExpressionUtils.as(
                                    JPAExpressions.select(member.nickname)
                                            .from(member)
                                            .where(member.id.eq(iconGroup.memberId)),
                                    "creatorNickname"),
                            iconGroup.thumbnailImageUrl, iconGroup.iconType, iconGroup.price
                    )
                )
                .from(iconGroup)
                .where(iconGroup.iconState.eq(IconState.REGISTERED), iconGroup.id.eq(iconGroupId))
                .fetchOne();
    }

    @Override
    public List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin) {
        return iconGroupJpaRepository.findAllByIconBuiltin(iconBuiltin);
    }

    @Override
    public List<IconGroup> findAllByMemberId(final long memberId) {
        return iconGroupJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<IconGroupInfo> findAllIconGroupInfoByMemberIdAndIconType(final long memberId, final IconType iconType){
        return queryFactory.select(
                    Projections.constructor(
                        IconGroupInfo.class,
                        iconGroup.id, iconGroup.name,
                        ExpressionUtils.as(
                                JPAExpressions.select(member.nickname)
                                        .from(member)
                                        .where(member.id.eq(iconGroup.memberId)),
                                "creatorNickname"),
                        iconGroup.thumbnailImageUrl, iconGroup.iconType, iconGroup.price
                    )
                )
                .from(iconGroup)
                .join(iconMember)
                .on(iconGroup.id.eq(iconMember.iconGroupId))
                .where(iconGroup.iconType.eq(iconType), iconGroup.iconState.eq(IconState.REGISTERED), iconMember.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public List<IconGroupInfo> findAllIconGroupInfoWithNonBuiltinAndRegisteredByIconType(final IconType iconType){
        return queryFactory.select(
                Projections.constructor(
                        IconGroupInfo.class,
                        iconGroup.id, iconGroup.name,
                        ExpressionUtils.as(
                                JPAExpressions.select(member.nickname)
                                        .from(member)
                                        .where(member.id.eq(iconGroup.memberId)),
                                "creatorNickname"), iconGroup.thumbnailImageUrl, iconGroup.iconType, iconGroup.price
                )
                )
                .from(iconGroup)
                .where(iconGroup.iconType.eq(iconType), iconGroup.iconBuiltin.eq(IconBuiltin.NONBUILTIN),
                        iconGroup.iconState.eq(IconState.REGISTERED))
                .fetch();
    }

    @Override
    public List<IconGroup> findAllByIconState(IconState iconState) {
        return iconGroupJpaRepository.findAllByIconState(iconState);
    }

    @Override
    public void deleteById(final long iconGroupId) {
        iconGroupJpaRepository.deleteById(iconGroupId);
    }

}
