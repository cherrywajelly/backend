package com.timeToast.timeToast.repository.jpa.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IconGroupJpaRepository extends JpaRepository<IconGroup, Long> {
    Optional<IconGroup> findByIdAndMemberId(final long iconGroupId, final long memberId);
    List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByIconState(final IconState iconState);
    List<IconGroup> findAllByMemberId(final long memberId);
}
