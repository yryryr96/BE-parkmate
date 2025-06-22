package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.EventDispatcher;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.scheduler.NotificationSendScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final NotificationService notificationService;
    private final NotificationSendScheduler scheduler;
    private final EventDispatcher eventDispatcher;
    private final ThreadPoolTaskExecutor dbThreadPool;

    public CompletableFuture<Void> handleEvent(NotificationEvent event) {

        EventProcessor<NotificationEvent> processor = eventDispatcher.dispatch(event);

        return processor.create(event)
                .thenCompose(notification -> {
                    return CompletableFuture.supplyAsync(() -> notificationService.create(notification), dbThreadPool);
                })
                .thenCompose(scheduler::addSchedule)
                .exceptionally(ex -> {
                    log.error("알림 생성 또는 스케줄링 처리 중 예외 발생: event={}", event, ex);
                    return null;
                })
                .thenAccept(v -> {
                    log.info("알림 생성 및 스케줄링 완료");
                });
    }
}
