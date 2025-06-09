package com.parkmate.parkingservice.kafka.producer;

import com.parkmate.parkingservice.parkinglotreactions.event.ReactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, ReactionCreatedEvent> parkingLotReactionsKafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReactionCreatedEvent(ReactionCreatedEvent event) {
        parkingLotReactionsKafkaTemplate.send("parking.parking-lot-reactions.created", event.getParkingLotUuid(), event);
    }
}
