package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;

import java.util.List;

public interface IconRepository {
    Icon getById(final long iconId);
    Icon getToastIconById(final long iconId, final boolean isOpen);
    List<Icon> findByIconGroupId(final long iconGroupId);
    Icon save(final Icon icon);
}
