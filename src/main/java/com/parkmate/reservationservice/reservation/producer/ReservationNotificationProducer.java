package com.parkmate.reservationservice.reservation.producer;

import com.parkmate.reservationservice.reservation.event.ReservationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationNotificationProducer {

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    private static final String TOPIC = "reservation.created";

    public void send(ReservationEvent reservationEvent) {

        kafkaTemplate.send(TOPIC, reservationEvent);
        log.info("Reservation notification sent: {}", reservationEvent);
    }
}
