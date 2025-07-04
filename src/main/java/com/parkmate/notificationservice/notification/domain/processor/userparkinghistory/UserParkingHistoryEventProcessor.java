package com.parkmate.notificationservice.notification.domain.processor.userparkinghistory;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.domain.NotificationType;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import com.parkmate.notificationservice.notification.event.userparkinghistory.UserParkingHistoryEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.notification.infrastructure.client.user.UserClient;
import com.parkmate.notificationservice.notification.infrastructure.client.user.response.UsernameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserParkingHistoryEventProcessor implements EventProcessor<UserParkingHistoryEvent> {

    private static final String ENTRY_NOTIFICATION_TITLE = "입차 알림";
    private static final String ENTRY_NOTIFICATION_TEMPLATE = "%s님이 %s에 입차하셨습니다.";
    private static final long ENTRY_NOTIFICATION_OFFSET_SECONDS = 10;
    private static final String EXIT_REMIND_NOTIFICATION_TITLE = "출차 알림";
    private static final String EXIT_REMIND_NOTIFICATION_TEMPLATE = "%s님, 출차 10분 전입니다.";
    private static final long EXIT_NOTIFICATION_OFFSET_MINUTES = 10L;

    private final UserClient userClient;

    @Override
    public boolean supports(NotificationEvent event) {
        return event instanceof UserParkingHistoryEvent;
    }

    @Override
    public CompletableFuture<List<Notification>> process(UserParkingHistoryEvent event) {

        String userUuid = event.getUserUuid();

        CompletableFuture<ApiResponse<UsernameResponse>> userNameFuture = userClient.getUsername(userUuid)
                .exceptionally(ex -> {
                    log.error("Failed to fetch user name for UUID: {}", event.getUserUuid(), ex);
                    return null;
                });

        return userNameFuture.thenApplyAsync(userNameResponse -> {

            String userName;
            if (userNameResponse == null || userNameResponse.getData() == null) {
                log.warn("User name response is null for UUID: {}", event.getUserUuid());
                userName = "사용자";
            } else {
                userName = userNameResponse.getData().getName();
            }

            String entryNotificationContent = String.format(ENTRY_NOTIFICATION_TEMPLATE, userName, event.getParkingSpotName());
            String exitReminderNotificationContent = String.format(EXIT_REMIND_NOTIFICATION_TEMPLATE, userName);

            Notification entryNotification = createEntryNotification(event,
                    entryNotificationContent,
                    LocalDateTime.now().plusSeconds(ENTRY_NOTIFICATION_OFFSET_SECONDS)
            );

            Notification exitRemindNotification = createRemindNotification(
                    event,
                    exitReminderNotificationContent,
                    event.getExitTime().minusMinutes(EXIT_NOTIFICATION_OFFSET_MINUTES));

            return List.of(entryNotification, exitRemindNotification);
        });
    }

    private Notification createEntryNotification(UserParkingHistoryEvent event,
                                                 String content,
                                                 LocalDateTime sendAt) {
        return Notification.builder()
                .receiverUuid(event.getUserUuid())
                .title(ENTRY_NOTIFICATION_TITLE)
                .content(content)
                .sendAt(sendAt)
                .status(NotificationStatus.PENDING)
                .type(event.getNotificationType())
                .build();
    }

    private Notification createRemindNotification(UserParkingHistoryEvent event,
                                                  String content,
                                                  LocalDateTime sendAt) {
        return Notification.builder()
                .receiverUuid(event.getUserUuid())
                .title(EXIT_REMIND_NOTIFICATION_TITLE)
                .content(content)
                .sendAt(sendAt)
                .status(NotificationStatus.PENDING)
                .type(NotificationType.PARKING_EXIT_REMINDER)
                .build();
    }
}
