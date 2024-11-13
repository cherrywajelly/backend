package com.timeToast.timeToast.repository.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;

import java.util.List;

public interface IconGroupRepository {
    IconGroup getById(final long iconGroupId);
    List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByMemberId(final long memberId);
    List<IconGroup> findAllByIconTypeAndIconBuiltin(final IconType iconType, final IconBuiltin iconBuiltin);
    IconGroup save(final IconGroup iconGroup);
    void deleteById(final long iconGroupId);
}
