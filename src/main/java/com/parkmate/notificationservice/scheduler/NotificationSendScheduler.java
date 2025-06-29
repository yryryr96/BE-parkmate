package com.parkmate.notificationservice.scheduler;

import com.parkmate.notificationservice.notification.application.NotificationService;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notificationsender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSendScheduler {

    private final NotificationSender notificationSender;
    private final NotificationService notificationService;
    private final TaskScheduler scheduler;

    @Async("schedulerExecutor")
    public CompletableFuture<Void> addSchedule(Notification notification) {

        scheduler.schedule(() -> {
            notificationSender.send(notification)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("알림 전송 실패: receiver={}, title={}, error={}",
                                    notification.getReceiverUuid(),
                                    notification.getTitle(),
                                    ex.getMessage(), ex);

//                            notificationService.updateNotificationStatus(notification, NotificationStatus.FAILED);
                        } else {
                            log.info("알림 전송 성공: messageId={}", result);
//                            notificationService.updateNotificationStatus(notification, NotificationStatus.SENT);
                        }
                    });
        }, notification.getSendAt().atZone(ZoneId.systemDefault()).toInstant());

        return CompletableFuture.completedFuture(null);
    }
}
