package com.parkmate.notificationservice.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public String sendNotification(String targetToken, String title, String body) throws FirebaseMessagingException {
        // 알림 메시지 (Notification Message) 생성
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                // .setImage("https://example.com/image.png") // 알림에 이미지 추가 (선택 사항)
                .build();

        // 메시지 빌더
        Message message = Message.builder()
                .setToken(targetToken) // 푸시 알림을 받을 클라이언트의 FCM 등록 토큰
                .setNotification(notification)
                // .putData("key1", "value1") // 데이터 메시지 추가 (선택 사항)
                .build();

        // 메시지 전송
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
        return response;
    }
}
