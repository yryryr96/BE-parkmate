package com.parkmate.notificationservice.notificationsender.firebase;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.parkmate.notificationservice.notificationsender.NotificationSender;
import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMService implements NotificationSender {

    private final UserTokenService userTokenService;

    @Async("fcmThreadPool")
    @Override
    public CompletableFuture<Void> send(com.parkmate.notificationservice.notification.domain.Notification notification) {

        String receiver = notification.getReceiverUuid();
        String title = notification.getTitle();
        String content = notification.getContent();

        String token = userTokenService.getTokenByUserUuid(receiver).getToken();

        Notification fcmNotification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(fcmNotification)
                .putData("type", notification.getType().toString())
                .putData("redirectUrl", notification.getRedirectUrl())
                .build();

        ApiFuture<String> sendResultFuture = FirebaseMessaging.getInstance().sendAsync(message);

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
        }, MoreExecutors.directExecutor());

        return resultCompletableFuture.thenApply(messageId -> null);
    }
}
