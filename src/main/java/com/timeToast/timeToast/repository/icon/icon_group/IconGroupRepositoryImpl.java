package com.timeToast.timeToast.repository.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_GROUP_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class IconGroupRepositoryImpl implements IconGroupRepository{
    private final IconGroupJpaRepository iconGroupJpaRepository;

    @Override
    public IconGroup getById(final long iconGroupId) { return iconGroupJpaRepository.findById(iconGroupId).orElseThrow(() -> new NotFoundException(ICON_GROUP_NOT_FOUND.getMessage())); }

    @Override
    public List<IconGroup> findByIconBuiltin(final IconBuiltin iconBuiltin) {
        return iconGroupJpaRepository.findByIconBuiltin(iconBuiltin);
    }
    @Override
    public IconGroup save(final IconGroup iconGroup) {
        return iconGroupJpaRepository.save(iconGroup);
    }
}
