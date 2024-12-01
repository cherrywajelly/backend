package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.enums.icon_group.ThumbnailIcon;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

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

    @Override
    public Icon getDefaultIcon() {
        return iconJpaRepository.getById(1L);
    }

    @Override
    public List<Icon> findAllByIconGroupId(final long iconGroupId){
        return iconJpaRepository.findAllByIconGroupId(iconGroupId);
    }

    @Override
    public Icon save(final Icon icon) {
        return iconJpaRepository.save(icon);
    }

    @Override
    public void deleteById(final long iconId) {
        iconJpaRepository.deleteById(iconId);
    }

    @Override
    public Icon findByIconImageUrl(final String iconImageUrl) {
        return iconJpaRepository.findByIconImageUrl(iconImageUrl);
    }
}
