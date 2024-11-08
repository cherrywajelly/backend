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

    public ObjectStorageClient getObjectStorage() throws IOException {
        final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault("DEFAULT");


        try {
            // ObjectStorageClient 생성
            BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

            ObjectStorageClient client = ObjectStorageClient.builder().build(provider);

            return client;
        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력
            throw new RuntimeException("Failed to create ObjectStorage client", e);
        }
    }
}
