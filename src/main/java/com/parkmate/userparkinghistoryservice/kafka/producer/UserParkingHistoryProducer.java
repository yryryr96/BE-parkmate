package com.parkmate.userparkinghistoryservice.kafka.producer;

import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserParkingHistoryProducer {

    private final KafkaTemplate<String, HistoryEvent> kafkaTemplate;

    private static final String TOPIC = "user-parking-history.history";

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void HistoryEventProducer(HistoryEvent event) {
        kafkaTemplate.send(TOPIC, event.getReservationCode(), event);
    }
}
