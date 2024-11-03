package com.timeToast.timeToast.global.config;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;

import java.io.IOException;


@Configuration
public class OsClientConfiguration {
    String configurationFilePath = "~/.oci/config";
    String profile = "DEFAULT";


    @Value("${spring.cloud.oci.region.static}")
    final String region ="";

    private final String urlPrefix = "http://os-axmpikvsv3z9.private.compat.objectstorage.ap-chuncheon-1.oci.customer-oci.com";


    public ObjectStorage getObjectStorage() throws IOException {

        //config 파일 읽어오기
        final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath, profile);

        // ObjectStorageClient 생성
        try {
            System.out.println(configFile);
            BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

            ObjectStorage client = ObjectStorageClient.builder()
                    .region(region)
                    .endpoint(urlPrefix)
                    .build(provider);

            return client;
        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력
            throw new RuntimeException("Failed to create ObjectStorage client", e);
        }
    }
}
