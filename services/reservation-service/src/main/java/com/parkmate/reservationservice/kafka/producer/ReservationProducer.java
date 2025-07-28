package com.parkmate.reservationservice.kafka.producer;

import com.parkmate.reservationservice.kafka.constant.KafkaTopics;
import com.parkmate.reservationservice.reservation.event.reservation.ReservationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationProducer {

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(ReservationEvent event) {

        kafkaTemplate.send(KafkaTopics.RESERVATION, event);
        log.info("Event sent to topic {}: {}", KafkaTopics.RESERVATION, event);
    }
}
