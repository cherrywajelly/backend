package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_NOT_FOUND;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IconRepositoryImpl implements IconRepository {
    private final IconJpaRepository iconJpaRepository;

    @Override
    public Icon getById(final long iconId) {
        return iconJpaRepository.findById(iconId).orElseThrow(() -> {
            log.warn("NotFoundException iconId={}", iconId);
            throw new NotFoundException(ICON_NOT_FOUND.getMessage());
            });
    }

}
