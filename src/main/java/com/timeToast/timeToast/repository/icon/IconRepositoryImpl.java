package com.timeToast.timeToast.repository.icon;

import com.timeToast.timeToast.domain.icon.Icon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IconRepositoryImpl implements IconRepository {
    private final IconJpaRepository iconJpaRepository;

    @Override
    public Icon save(final Icon icon) {
        return iconJpaRepository.save(icon);
    }
}
