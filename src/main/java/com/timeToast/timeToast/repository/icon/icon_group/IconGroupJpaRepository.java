package com.timeToast.timeToast.repository.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IconGroupJpaRepository extends JpaRepository<IconGroup, Long> {
    List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByIconTypeAndIconBuiltin(final IconType iconType, final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByMemberId(final long memberId);
}
