package com.timeToast.timeToast.service;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.model.PreauthenticatedRequest;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.timeToast.timeToast.global.config.OsClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    String bucketName = "timetoast";

    String namespaceName = "axmpikvsv3z9";

    private final String urlPrefix =
            "https://objectstorage.ap-chuncheon-1.oraclecloud.com/p/X1gS5Vanwz7flVOyuNlbh0Lw-n38Vas1fSpNvycbONlfq4A2BOV79j8vGJF-dwxy/n/axmpikvsv3z9/b/timetoast_bucket/o/";

    private final OsClientConfiguration ociConfig;

    public void upload(List<MultipartFile> files) {

        files.forEach(file -> {
            System.out.println(file.getOriginalFilename());
            try {
                InputStream inputStream = file.getInputStream();
                System.out.println(inputStream);
                //build upload request
                PutObjectRequest putObjectRequest =
                        PutObjectRequest.builder()
                                .namespaceName(namespaceName)
                                .bucketName(bucketName)
                                .objectName(UUID.randomUUID().toString())
                                .putObjectBody(inputStream)
                                .build();
                System.out.println(putObjectRequest);
                System.out.println("===============");

                ObjectStorage storage = ociConfig.getObjectStorage();
                if (storage != null) {
                    System.out.println("!storage is full");
                    ociConfig.getObjectStorage().putObject(putObjectRequest);
                } else {
                    System.err.println("ObjectStorage_client_is_null. Cannot perform putObject.");
                }
                //upload file
//                ociConfig.getObjectStorage().putObject(putObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally{
//                try {
//                    ociConfig.getObjectStorage().close();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
            }

        });

    }

    public List<String> getFileObject(List<String> iconIds) throws Exception{
        List<String> accessUris = new ArrayList<>();

        iconIds.forEach(iconId -> {
            CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails =
                    CreatePreauthenticatedRequestDetails.builder()
                            .objectName(iconId)
                            //readonly access
                            .accessType(CreatePreauthenticatedRequestDetails.AccessType.ObjectRead)
                            //here we set expiration time as 1 hour
                            .timeExpires(Date.from(Instant.now().plusSeconds(3600))).build();

            //Build request
            CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest =
                    CreatePreauthenticatedRequestRequest.builder()
                            .namespaceName(namespaceName)
                            .bucketName(bucketName)
                            .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails)
                            .opcClientRequestId(UUID.randomUUID().toString()).build();

            // send request to oci
            try {
                CreatePreauthenticatedRequestResponse response =
                        ociConfig.getObjectStorage()
                                .createPreauthenticatedRequest(createPreauthenticatedRequestRequest);


                String accessUri = response.getPreauthenticatedRequest().getAccessUri();
                accessUris.add(urlPrefix+accessUri);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        // Build request details

        ociConfig.getObjectStorage().close();
        return accessUris;
    }

}
