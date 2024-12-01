package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.enums.icon_group.ThumbnailIcon;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;


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
    public Map<MultipartFile, String> postIconSet(List<MultipartFile> files, final long iconGroupId) {
        Map<MultipartFile, String> imageUrls = new HashMap<>();

        files.forEach(file->{
            Icon icon = iconRepository.save(new Icon("", iconGroupId));
            String endpoint = baseUrl + "icon/image/" + Long.toString(icon.getId());
            String imageUrl = fileUploadService.uploadfile(file, endpoint);
            icon.updateUrl(imageUrl);
            iconRepository.save(icon);
            imageUrls.put(file, imageUrl);
        });

        log.info("save icon images");
        return imageUrls;
    }
}
