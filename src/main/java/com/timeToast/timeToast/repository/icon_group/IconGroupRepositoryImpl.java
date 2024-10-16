package com.timeToast.timeToast.repository.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICONGROUP_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class IconGroupRepositoryImpl implements IconGroupRepository{
    private final IconGroupJpaRepository iconGroupJpaRepository;

    @Override
    public IconGroup getById(final long iconGroupId) { return iconGroupJpaRepository.findById(iconGroupId).orElseThrow(() -> new NotFoundException(ICONGROUP_NOT_FOUND.getMessage())); }

    @Override
    public List<IconGroup> findByIconType(final IconType iconType) {
        return iconGroupJpaRepository.findByIconType(iconType);
    }
    @Override
    public IconGroup save(final IconGroup iconGroup) {
        return iconGroupJpaRepository.save(iconGroup);
    }
}
