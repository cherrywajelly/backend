package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.icon.request.IconPostRequest;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_GROUP_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconServiceImpl implements IconService{
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;

    @Transactional
    public void postIconSet(List<IconPostRequest> iconPostRequestSet, long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        if(iconGroup == null) {
            throw new BadRequestException(INVALID_ICON.getMessage());
        } else {
            // TODO s3 이미지 저장 로직으로 수정
            for (IconPostRequest iconPostRequest : iconPostRequestSet) {
                iconRepository.save(iconPostRequest.toEntity(iconPostRequest, iconGroupId));
            }
            log.info("save icon images");
        }

    }
}
