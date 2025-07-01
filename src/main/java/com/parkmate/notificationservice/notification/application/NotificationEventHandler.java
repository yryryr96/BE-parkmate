package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.EventDispatcher;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.scheduler.NotificationSendScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

        List<CompletableFuture<List<Notification>>> notificationCreationFutures = events.stream()
                .map(event -> {
                    EventProcessor<NotificationEvent> processor = eventDispatcher.dispatch(event);
                    return processor.create(event)
                            .exceptionally(ex -> {
                                log.error("개별 알림 리스트 생성 중 예외 발생: event={}, {}", event.getClass().getSimpleName(), ex.getMessage(), ex);
                                return List.of();
                            });
                })
                .toList();

        return CompletableFuture.allOf(notificationCreationFutures.toArray(new CompletableFuture[0]))
                .thenApplyAsync(v -> notificationCreationFutures.stream()
                        .flatMap(cf -> cf.join().stream())
                        .toList()
                )
                .thenCompose(notifications -> {
                    if (notifications.isEmpty()) {
                        log.warn("생성된 알림이 없습니다. DB 저장 및 스케줄링을 건너뜁니다.");
                        return CompletableFuture.completedFuture(null);
                    }

                    return CompletableFuture.supplyAsync(() -> notificationService.bulkInsert(notifications), dbThreadPool);
                })
                .thenCompose(savedNotifications -> {
                    List<CompletableFuture<Void>> scheduleFutures = savedNotifications.stream()
                            .map(scheduler::addSchedule)
                            .toList();

                    return CompletableFuture.allOf(scheduleFutures.toArray(new CompletableFuture[0]))
                            .thenAccept(v -> {
                                log.info("모든 알림 스케줄링 등록 완료. 총 {}건", savedNotifications.size());
                            });

                })
                .exceptionally(ex -> {
                    log.error("알림 처리 체인 중 예외 발생: {}", ex.getMessage(), ex);
                    throw new RuntimeException("알림 처리 실패: " + ex.getMessage(), ex);
                })
                .thenAccept(v -> {
                    log.info("알림 생성 및 스케줄링 완료 endTime: {}", LocalDateTime.now());
                });
    }
}
