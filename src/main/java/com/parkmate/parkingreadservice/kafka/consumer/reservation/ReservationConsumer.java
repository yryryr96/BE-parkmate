package com.parkmate.parkingreadservice.kafka.consumer.reservation;

import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;
import com.parkmate.parkingreadservice.kafka.eventmanager.ReservationEventManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationConsumer {

    private final ReservationEventManager reservationEventManager;

    @KafkaListener(
            topics = KafkaTopics.RESERVATION_CREATED,
            containerFactory = "reservationCreateKafkaListener"
    )
    public void consumeReservationCreatedEvent(ReservationCreateEvent event) {
        log.info("Received reservation created event: {}", event);
        reservationEventManager.handleReservationCreatedEvent(event);
    }
}
