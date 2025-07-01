package com.parkmate.notificationservice.notificationsender.firebase;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.*;
import com.parkmate.notificationservice.notificationsender.NotificationSender;
import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import com.parkmate.notificationservice.usertoken.domain.UserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        List<String> tokens = userTokenService.getTokenByUserUuid(receiver).stream()
                .map(UserToken::getToken)
                .toList();

        Notification fcmNotification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(fcmNotification)
                .putData("type", notification.getType().toString())
                .putData("redirectUrl", notification.getRedirectUrl())
                .addAllTokens(tokens)
                .build();

        ApiFuture<BatchResponse> sendResultFuture = FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);

        CompletableFuture<Void> resultCompletableFuture = new CompletableFuture<>();
        ApiFutures.addCallback(sendResultFuture, new ApiFutureCallback<BatchResponse>() {
            @Override
            public void onSuccess(BatchResponse response) {

                log.info("FCM 알림 전송 결과: receiver={}, successfulCount={}, failureCount={}",
                        receiver, response.getSuccessCount(), response.getFailureCount());

                if (response.getFailureCount() > 0) {
                    List<String> tokensToDelete = new ArrayList<>();
                    for (int i = 0; i < response.getResponses().size(); i++) {
                        SendResponse sendResponse = response.getResponses().get(i);
                        if (!sendResponse.isSuccessful()) {
                            MessagingErrorCode errorCode = sendResponse.getException().getMessagingErrorCode();
                            String failedToken = tokens.get(i);

                            log.warn("FCM 알림 전송 실패 토큰: token={}, errorCode={}, errorMessage={}",
                                    failedToken, errorCode, sendResponse.getException().getMessage());

                            if (errorCode == MessagingErrorCode.UNREGISTERED ||
                                errorCode == MessagingErrorCode.INVALID_ARGUMENT ||
                                errorCode == MessagingErrorCode.SENDER_ID_MISMATCH) {
                                tokensToDelete.add(failedToken);
                            }
                        }
                    }

                    if (!tokensToDelete.isEmpty()) {
                        log.info("DB에서 삭제할 FCM 토큰 수: receiver={}, count={}", receiver, tokensToDelete.size());
                        userTokenService.deleteTokens(tokensToDelete);
                    }
                }
                resultCompletableFuture.complete(null);
            }

            @Override
            public void onFailure(Throwable t) {
                resultCompletableFuture.completeExceptionally(t);
                log.error("FCM 알림 전송 실패 (Multicast 전체 오류): receiver={}, title={}, error={}", receiver, title, t.getMessage(), t);
            }
        }, MoreExecutors.directExecutor());

        return resultCompletableFuture;
    }
}
