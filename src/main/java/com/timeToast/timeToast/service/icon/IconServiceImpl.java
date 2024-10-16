package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.request.IconPostRequest;
import com.timeToast.timeToast.repository.icon.IconRepository;
import com.timeToast.timeToast.repository.icon_group.IconGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class IconServiceImpl implements IconService{
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;

    public void postIconSet(Set<IconPostRequest> iconPostRequestSet, long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        // TODO s3 이미지 저장 로직
        for (IconPostRequest iconPostRequest : iconPostRequestSet) {
            iconRepository.save(iconPostRequest.toEntity(iconPostRequest, iconGroup));
        }
    }
}
