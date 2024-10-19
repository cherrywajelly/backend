package com.timeToast.timeToast.repository.icon;

import com.timeToast.timeToast.domain.icon.Icon;

public interface IconRepository {
    Icon getById(final long iconId);
    Icon save(final Icon icon);
}
