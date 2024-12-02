//package com.timeToast.timeToast.global.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.List;
//
//@Configuration
//public class FirebaseConfig {
//
//    @Value("${fcm.path}")
//    private String fcmPath;
//
//    @Value("${fcm.credential}")
//    private String fcmCredential;
//
//    @PostConstruct
//    public void initializeFirebase() throws IOException {
//        try {
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseOptions options = FirebaseOptions.builder()
//                        .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(fcmPath).getInputStream()))
//                        .setDatabaseUrl(fcmPath)
//                        .build();
//
//                FirebaseApp.initializeApp(options);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//}