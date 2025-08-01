package com.timeToast.timeToast.repository.jpa.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.response.IconGroupInfo;

import java.util.List;

public interface IconGroupRepository {
    IconGroup save(final IconGroup iconGroup);
    IconGroup getById(final long iconGroupId);
    IconGroup getByIdAndMemberId(final long memberId, final long iconGroupId);
    IconGroupInfo getIconGroupInfo(final long memberId, final long iconGroupId);
    List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin);
    List<IconGroup> findAllByMemberId(final long memberId);
    List<IconGroupInfo> findAllIconGroupInfoByMemberIdAndIconType(final long memberId, final IconType iconType);
    List<IconGroupInfo> findAllIconGroupInfoWithNonBuiltinAndRegisteredByIconType(final IconType iconType);
    List<IconGroup> findAllByIconState(final IconState iconState);
    void deleteById(final long iconGroupId);
}
