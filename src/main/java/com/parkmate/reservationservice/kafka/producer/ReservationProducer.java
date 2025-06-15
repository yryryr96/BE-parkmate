package com.parkmate.reservationservice.kafka.producer;

import com.parkmate.reservationservice.kafka.event.ReservationCreateEvent;
import com.parkmate.reservationservice.kafka.properties.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationProducer {

    private final KafkaTemplate<String, ReservationCreateEvent> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(ReservationCreateEvent reservationCreateEvent) {

        kafkaTemplate.send(KafkaTopics.reservationCreated, reservationCreateEvent);
        log.info("Reservation notification sent: {}", reservationCreateEvent);
    }
}
