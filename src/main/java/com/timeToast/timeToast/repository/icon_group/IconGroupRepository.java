package com.timeToast.timeToast.repository.icon_group;

import com.timeToast.timeToast.domain.icon_group.IconGroup;

public interface IconGroupRepository {
    IconGroup getById(final long iconGroupId);
    IconGroup save(final IconGroup iconGroup);
}
