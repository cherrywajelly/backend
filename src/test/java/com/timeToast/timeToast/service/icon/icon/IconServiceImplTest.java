package com.timeToast.timeToast.service.icon.icon;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.image.FileUploadServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IconServiceImplTest {
    @Mock
    private IconRepository iconRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    OsClientConfiguration ociConfig;

    @InjectMocks
    FileUploadServiceImpl fileUploadService;

    @InjectMocks
    IconServiceImpl iconService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Value("${spring.cloud.oci.url.static}")
    private String urlPrefix;

    @Test
    @DisplayName("아이콘 이미지 저장 - 성공")
    void saveToken() {
        // Given
        long iconGroupId = 1L;
        List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class));

        IconGroup iconGroup = IconGroup.builder().build();

        Icon icon = new Icon("", iconGroupId);

        String imageUrl = baseUrl+"icon/image/1";
        ObjectStorageClient storage = mock(ObjectStorageClient.class);

        try {
            when(ociConfig.getObjectStorage()).thenReturn(storage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);
        when(iconRepository.save(new Icon("", iconGroupId))).thenReturn(icon);
        when(fileUploadService.uploadfile(any(MultipartFile.class), any(String.class)))
                .thenReturn(urlPrefix + imageUrl);



        // When
        Response response = iconService.postIconSet(files, iconGroupId);

        // Then
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }
}
