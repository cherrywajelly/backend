package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class IconRepositoryImpl implements IconRepository {
    private final IconJpaRepository iconJpaRepository;

    @Override
    public Icon getById(final long iconId) { return iconJpaRepository.findById(iconId).orElseThrow(() -> new NotFoundException(ICON_NOT_FOUND.getMessage())); }

    @Override
    public Icon save(final Icon icon) {
        return iconJpaRepository.save(icon);
    }
}
