package com.parkmate.reservationservice.kafka.producer;

import com.parkmate.reservationservice.kafka.event.ReservationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationNotificationProducer {

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    private static final String TOPIC = "reservation.reservation.created";

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(ReservationEvent reservationEvent) {

        kafkaTemplate.send(TOPIC, reservationEvent);
        log.info("Reservation notification sent: {}", reservationEvent);
    }
}
