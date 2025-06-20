package com.parkmate.notificationservice.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
public class FireBaseInitializer {

    // serviceAccountPath 대신 환경 변수에서 직접 JSON 내용을 받아옴
    // 환경 변수 이름은 FIREBASE_SERVICE_ACCOUNT_JSON 또는 FIREBASE_SERVICE_ACCOUNT_BASE64 등 원하는 대로 설정
    @Value("${firebase.service-account-json-base64}") // application.yml에서 이 키가 없으면 빈 문자열 사용
    private String firebaseServiceAccountBase64;

    @PostConstruct
    public void initialize() throws IOException {
        if (firebaseServiceAccountBase64 == null || firebaseServiceAccountBase64.isEmpty()) {
            System.err.println("Firebase service account JSON (Base64 encoded) is not set. Skipping Firebase initialization.");
            return; // 환경 변수가 없으면 초기화 건너뛰기
        }

        // Base64 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(firebaseServiceAccountBase64);
        try (InputStream serviceAccount = new ByteArrayInputStream(decodedBytes)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Admin SDK initialized successfully from environment variable.");
            }
        } catch (IOException e) {
            System.err.println("Error initializing Firebase Admin SDK from environment variable: " + e.getMessage());
            throw e;
        }
    }
}
