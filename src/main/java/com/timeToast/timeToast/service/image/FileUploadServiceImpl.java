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

    @Value("${spring.cloud.oci.bucketname.static}")
    private String bucketName;

    @Value("${spring.cloud.oci.namespace.static}")
    private String namespace;


    @Value("${spring.cloud.oci.url.static}")
    private String urlPrefix;

    private final OsClientConfiguration ociConfig;

    //oci 업로드
    @Transactional
    @Override
    public String uploadfile(final MultipartFile file, final String endpoint) {

        try {
            InputStream inputStream = file.getInputStream();

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
        } catch (IOException e) {
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
