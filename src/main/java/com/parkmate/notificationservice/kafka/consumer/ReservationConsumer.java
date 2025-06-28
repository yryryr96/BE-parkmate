package com.parkmate.notificationservice.kafka.consumer;

import com.parkmate.notificationservice.kafka.constant.KafkaTopics;
import com.parkmate.notificationservice.notification.application.NotificationEventHandler;
import com.parkmate.notificationservice.notification.domain.event.reservation.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReservationConsumer {

    private final NotificationEventHandler eventHandler;

    @KafkaListener(
            topics = KafkaTopics.RESERVATION_CREATED,
            containerFactory = "reservationCreatedContainerFactory",
            concurrency = "3"
    )
    public CompletableFuture<Void> consumeReservationCreatedEvent(List<ReservationCreatedEvent> events) {
        return eventHandler.handleEvent(events);
    }
}
