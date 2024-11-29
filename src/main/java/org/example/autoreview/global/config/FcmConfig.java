package org.example.autoreview.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FcmConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("ori-push-notification-firebase-adminsdk-k9qqe-d0581b0c52.json");

        try (InputStream refreshToken = resource.getInputStream()) {
            FirebaseApp firebaseApp = null;
            List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

            if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
                for (FirebaseApp app : firebaseAppList) {
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                        firebaseApp = app;
                        break; // 이미 기본 앱이 존재하면 반복 종료
                    }
                }
            }
            if (firebaseApp == null) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(refreshToken))
                        .build();
                firebaseApp = FirebaseApp.initializeApp(options);
            }
            return FirebaseMessaging.getInstance(firebaseApp);
        } catch (IOException e) {
            // 예외 처리 추가
            throw new IOException("Failed to initialize Firebase Messaging: " + e.getMessage(), e);
        }
    }
}
