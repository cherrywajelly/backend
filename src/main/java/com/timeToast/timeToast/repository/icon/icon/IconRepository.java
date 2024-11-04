package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;

public interface IconRepository {
    Icon getById(final long iconId);
    Icon getToastIconById(final long iconId, final boolean isOpen);
    Icon save(final Icon icon);
}
