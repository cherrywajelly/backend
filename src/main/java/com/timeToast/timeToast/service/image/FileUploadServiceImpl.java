package com.timeToast.timeToast.service.image;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import com.timeToast.timeToast.global.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
                log.info("file still uploading");
                log.info(endpoint);
                ociConfig.getObjectStorage().putObject(putObjectRequest);
                return urlPrefix + putObjectRequest.getObjectName();
            } else {
                //전송 형식
                throw new BadRequestException("ObjectStorage_client_is_null. Cannot perform putObject.");
            }
        } catch (IOException e) {
            // ObjectStorageClient
            throw new RuntimeException(e);

        } finally {
            try {
                ociConfig.getObjectStorage().close();
            } catch (Exception e) {
                //t서버
                throw new RuntimeException(e);
            }
        }
    }

}
