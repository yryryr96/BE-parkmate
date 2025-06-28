package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.EventDispatcher;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.scheduler.NotificationSendScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final NotificationService notificationService;
    private final NotificationSendScheduler scheduler;
    private final EventDispatcher eventDispatcher;
    private final ThreadPoolTaskExecutor dbThreadPool;

    public CompletableFuture<Void> handleEvent(List<? extends NotificationEvent> events) {

        log.info("알림 처리 시작 startTime: {}, size: {}", LocalDateTime.now(), events.size());

        List<Notification> notifications = events.stream()
                .map(e -> {
                    EventProcessor<NotificationEvent> processor = eventDispatcher.dispatch(e);
                    return processor.create(e);
                }).flatMap(Collection::stream)
                .toList();

        return CompletableFuture.supplyAsync(() -> notificationService.bulkInsert(notifications), dbThreadPool)
                .thenAccept(savedNotifications -> savedNotifications.forEach(scheduler::addSchedule))
                .exceptionally(ex -> {
                    log.error("알림 생성 또는 스케줄링 처리 중 예외 발생: {}", ex);
                    return null;
                })
                .thenAccept(v -> {
                    log.info("알림 생성 및 스케줄링 완료 endTime: {}", LocalDateTime.now());
                });
    }
}
