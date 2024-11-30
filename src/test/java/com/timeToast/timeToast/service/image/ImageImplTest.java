package com.timeToast.timeToast.service.image;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageImplTest {

    @Value("${spring.cloud.oci.url.static}")
    private String urlPrefix;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @InjectMocks
    FileUploadServiceImpl fileUploadService;

    @Mock
    OsClientConfiguration ociConfig;

    private MultipartFile file;
    private ObjectStorageClient storage;


    @BeforeEach
    void setUp() {
        file = mock(MultipartFile.class);
        storage = mock(ObjectStorageClient.class);
    }


    @Test
    @DisplayName("이미지 업로드 - 성공")
    void saveFile() {
        // Given
        String endpoint = baseUrl;

        try {
            when(ociConfig.getObjectStorage()).thenReturn(storage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // When
        String imageUrl = fileUploadService.uploadfile(file, endpoint);

        // Then
        assertThat(imageUrl).isEqualTo(urlPrefix+endpoint);
    }
}
