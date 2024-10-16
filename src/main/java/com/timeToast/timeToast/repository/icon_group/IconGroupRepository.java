package com.timeToast.timeToast.repository.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon_group.IconGroup;

import java.util.List;

public interface IconGroupRepository {
    IconGroup getById(final long iconGroupId);
    List<IconGroup> findByIconType(final IconType iconType);
    IconGroup save(final IconGroup iconGroup);
}
