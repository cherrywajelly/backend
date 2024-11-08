package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconServiceImpl implements IconService{
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public void postIconSet(List<MultipartFile> files, long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        if(iconGroup == null) {
            throw new BadRequestException(INVALID_ICON.getMessage());
        } else {
            files.forEach(file->{
                Icon icon = iconRepository.save(new Icon("", iconGroupId));
                String endpoint = "icon/image/" + Long.toString(icon.getId());
                String imageUrls = fileUploadService.uploadImages(file, endpoint);
                icon.updateUrl(imageUrls);
                iconRepository.save(icon);
            });
            log.info("save icon images");
        }
    }
}
