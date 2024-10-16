package com.timeToast.timeToast.repository.icon_group;

import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class IconGroupRepositoryImpl implements IconGroupRepository{
    private final IconGroupJpaRepository iconGroupJpaRepository;

    @Override
    public IconGroup getById(final long iconGroupId) { return iconGroupJpaRepository.findById(iconGroupId).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND.getMessage())); }

    @Override
    public IconGroup save(final IconGroup iconGroup) {
        return iconGroupJpaRepository.save(iconGroup);
    }
}
