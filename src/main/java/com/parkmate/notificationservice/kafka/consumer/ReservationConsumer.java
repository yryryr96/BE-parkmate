package com.parkmate.notificationservice.kafka.consumer;

import com.parkmate.notificationservice.kafka.constant.KafkaTopics;
import com.parkmate.notificationservice.notification.application.NotificationEventHandler;
import com.parkmate.notificationservice.notification.domain.event.reservation.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReservationConsumer {

    private final NotificationEventHandler eventHandler;

    @KafkaListener(
            topics = KafkaTopics.RESERVATION_CREATED,
            containerFactory = "reservationCreatedContainerFactory"
    )
    public CompletableFuture<Void> consumeReservationCreatedEvent(ReservationCreatedEvent event) {
        return eventHandler.handleEvent(event);
    }
}
