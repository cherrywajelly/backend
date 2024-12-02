package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.enums.icon_group.ThumbnailIcon;
import com.timeToast.timeToast.domain.icon.icon.Icon;

import java.util.List;

public interface IconRepository {
    Icon getById(final long iconId);
    Icon getDefaultIcon();
    List<Icon> findAllByIconGroupId(final long iconGroupId);
    Icon save(final Icon icon);
    void deleteById(final long iconId);
    Icon findByIconImageUrl(final String iconImageUrl);
}
