package com.timeToast.timeToast.repository.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;

import java.util.List;
import java.util.Optional;

public interface IconGroupRepository {
    IconGroup save(final IconGroup iconGroup);
    IconGroup getById(final long iconGroupId);
    IconGroup getByIdAndMemberId(final long memberId, final long iconGroupId);
    IconGroupSummaryInfo getIconGroupSummaryInfo(final long memberId, final long iconGroupId);
    List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByMemberId(final long memberId);
    List<IconGroupSummaryInfo> findAllIconGroupSummaryInfoMemberAndIconType(final long memberId, final IconType iconType);
    List<IconGroupSummaryInfo> findAllIconGroupSummaryInfoWithNonBuiltinAndRegisteredByIconType(final IconType iconType);
    List<IconGroup> findAllByIconState(final IconState iconState);
    void deleteById(final long iconGroupId);
}
