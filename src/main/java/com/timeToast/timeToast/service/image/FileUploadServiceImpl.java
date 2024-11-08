package com.timeToast.timeToast.service.image;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${spring.cloud.oci.bucketname}")
    private String bucketName;

    @Value("${spring.cloud.oci.namespace}")
    private String namespace;

    @Value("${spring.cloud.oci.url}")
    private String urlPrefix;

    private final OsClientConfiguration ociConfig;

    //이미지 업로드
    @Transactional
    @Override
    public String uploadImages(MultipartFile file, String endpoint) {

        try {
            InputStream inputStream = file.getInputStream();
            upload(endpoint, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    //텍스트 업로드
    @Transactional
    @Override
    public String uploadTexts(String text, String endpoint) {

        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        upload(endpoint, inputStream);

        return null;
    }


    //oci 업로드
    @Transactional
    public String upload(String endpoint, InputStream inputStream) {
        try {
            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .namespaceName(namespace)
                            .bucketName(bucketName)
                            .objectName(endpoint)
                            .putObjectBody(inputStream)
                            .build();

            ObjectStorageClient storage = ociConfig.getObjectStorage();
            if (storage != null) {
                ociConfig.getObjectStorage().putObject(putObjectRequest);
                return urlPrefix + putObjectRequest.getObjectName();
            } else {
                log.error("ObjectStorage_client_is_null. Cannot perform putObject.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                ociConfig.getObjectStorage().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
