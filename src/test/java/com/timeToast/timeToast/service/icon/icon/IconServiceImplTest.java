package com.timeToast.timeToast.service.icon.icon;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class IconServiceImplTest {
    @Mock
    private IconRepository iconRepository;

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private IconServiceImpl iconService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Value("${spring.cloud.oci.url.static}")
    private String urlPrefix;

    private IconGroup iconGroup;
    private Icon icon;

    @BeforeEach
    void setUp() {
        iconGroup = IconGroup.builder().build();
        icon = Icon.builder().build();
    }

    @Test
    @DisplayName("아이콘 이미지 저장 - 성공")
    void saveToken() {
        // Given
        long iconGroupId = 1L;
        List<MultipartFile> files = Arrays.asList();

        // When
        iconService.postIconSet(files, iconGroupId);

        // Then
//        assertThat(response).isNotNull();
    }
}
