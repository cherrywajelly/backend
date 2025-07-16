package com.timeToast.timeToast.repository.jpa.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;

public interface IconRepository {
    Icon getById(final long iconId);
}
