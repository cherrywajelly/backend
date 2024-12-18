package com.timeToast.timeToast.repository.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_GROUP_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class IconGroupRepositoryImpl implements IconGroupRepository{
    private final IconGroupJpaRepository iconGroupJpaRepository;

    @Override
    public IconGroup getById(final long iconGroupId) { return iconGroupJpaRepository.findById(iconGroupId).orElseThrow(() -> new NotFoundException(ICON_GROUP_NOT_FOUND.getMessage())); }

    @Override
    public List<IconGroup> findAllByIconBuiltin(final IconBuiltin iconBuiltin) {
        return iconGroupJpaRepository.findAllByIconBuiltin(iconBuiltin);
    }

    @Override
    public List<IconGroup> findAllByMemberId(final long memberId) {
        return iconGroupJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<IconGroup> findAllByIconTypeAndIconBuiltin(final IconType iconType, final IconBuiltin iconBuiltin, final IconState iconState){
        return iconGroupJpaRepository.findAllByIconTypeAndIconBuiltinAndIconState(iconType, iconBuiltin,iconState);
    }

    @Override
    public List<IconGroup> findAllByIconState(IconState iconState) {
        return iconGroupJpaRepository.findAllByIconState(iconState);
    }

    @Override
    public IconGroup save(final IconGroup iconGroup) {
        return iconGroupJpaRepository.save(iconGroup);
    }


    @Override
    public void deleteById(final long iconGroupId) {
        iconGroupJpaRepository.deleteById(iconGroupId);
    }

    @Override
    public Optional<IconGroup> getByIdAndMemberId(final long iconGroupId, final long memberId) {
        return iconGroupJpaRepository.findByIdAndMemberId(iconGroupId, memberId);
    }
}
