package com.parkmate.notificationservice.firebase;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMService {

    private final UserTokenService userTokenService;

    @Async("fcmThreadPool") // 알림 전용 스레드 풀 (권장)
    public CompletableFuture<Void> sendNotificationAsync(String receiver, String title, String content) {
        return CompletableFuture.supplyAsync(() -> {

            String token = userTokenService.getTokenByUserUuid(receiver).getToken();

            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            ApiFuture<String> sendResultFuture = FirebaseMessaging.getInstance().sendAsync(message);

            // ApiFuture를 CompletableFuture로 변환
            CompletableFuture<String> resultCompletableFuture = new CompletableFuture<>();
            ApiFutures.addCallback(sendResultFuture, new ApiFutureCallback<String>() {
                @Override
                public void onSuccess(String messageId) {
                    resultCompletableFuture.complete(messageId);
                    log.info("FCM 알림 전송 성공: messageId={}", messageId);
                }

                @Override
                public void onFailure(Throwable t) {
                    resultCompletableFuture.completeExceptionally(t);
                    log.error("FCM 알림 전송 실패: receiver={}, title={}, error={}", receiver, title, t.getMessage(), t);
                }
            }, MoreExecutors.directExecutor()); // 콜백 실행 스레드 (여기서는 호출 스레드)

            return null;
        });
    }
}
