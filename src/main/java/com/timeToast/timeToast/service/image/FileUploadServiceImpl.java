package com.timeToast.timeToast.service.image;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    String bucketName = "timetoast_bucket";

    String namespace = "axmpikvsv3z9";

    private final String urlPrefix = "https://axmpikvsv3z9.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axmpikvsv3z9/b/timetoast_bucket/o/";

    private final OsClientConfiguration ociConfig;

    public String upload(MultipartFile file, String id) {

        try {
            InputStream inputStream = file.getInputStream();

            //request 생성
            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .namespaceName(namespace)
                            .bucketName(bucketName)
                            .objectName(id)
                            .putObjectBody(inputStream)
                            .build();

            ObjectStorageClient storage = ociConfig.getObjectStorage();
            if (storage != null) {
                ociConfig.getObjectStorage().putObject(putObjectRequest);
                return urlPrefix+putObjectRequest.getObjectName();
            } else {
                System.err.println("ObjectStorage_client_is_null. Cannot perform putObject.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            try {
                ociConfig.getObjectStorage().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}