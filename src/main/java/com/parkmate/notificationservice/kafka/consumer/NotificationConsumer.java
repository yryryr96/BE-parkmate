package com.parkmate.notificationservice.kafka.consumer;

import com.parkmate.notificationservice.notification.application.NotificationService;
import com.parkmate.notificationservice.notification.domain.NotificationEventDispatcher;
import com.parkmate.notificationservice.notification.domain.ReservationEvent;
import com.parkmate.notificationservice.notification.domain.ReservationEventProcessor;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final ReservationEventProcessor reservationEventProcessor;

    @KafkaListener(topics = {"reservation.created"}, groupId = "notification-service", containerFactory = "notificationEventListener")
    public Mono<Void> consumeNotificationEvent(ReservationEvent event) {
        return notificationService.create(event);
    }
}
