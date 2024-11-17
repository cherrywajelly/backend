package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class IconServiceImpl implements IconService{

    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final FileUploadService fileUploadService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public void postIconSet(List<MultipartFile> files, final long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        files.forEach(file->{
            Icon icon = iconRepository.save(new Icon("", iconGroup.getId()));
            String endpoint = baseUrl + "icon/image/" + Long.toString(icon.getId());
            System.out.println(endpoint);
            String imageUrls = fileUploadService.uploadfile(file, endpoint);
            icon.updateUrl(imageUrls);
            iconRepository.save(icon);
        });

        log.info("save icon images");

    }
}
