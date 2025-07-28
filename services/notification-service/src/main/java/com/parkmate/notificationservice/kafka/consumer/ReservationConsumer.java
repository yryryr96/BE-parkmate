package com.parkmate.notificationservice.kafka.consumer;

import com.parkmate.notificationservice.kafka.constant.KafkaTopics;
import com.parkmate.notificationservice.notification.application.NotificationEventHandler;
import com.parkmate.notificationservice.notification.event.reservation.ReservationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationConsumer {

    private final NotificationEventHandler eventHandler;

    @KafkaListener(
            topics = KafkaTopics.RESERVATION,
            containerFactory = "reservationCreatedContainerFactory")
    public void consumeReservationCreatedEvent(List<ReservationEvent> events) {
        eventHandler.handle(events);
    }
}
