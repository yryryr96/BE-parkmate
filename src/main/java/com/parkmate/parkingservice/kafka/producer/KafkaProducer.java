package com.parkmate.parkingservice.kafka.producer;

import com.parkmate.parkingservice.kafka.properties.KafkaTopicProperties;
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
    private final KafkaTopicProperties kafkaTopicProperties;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReactionCreatedEvent(ReactionCreatedEvent reactionCreatedEvent) {
        parkingLotReactionsKafkaTemplate.send(
                kafkaTopicProperties.getParkingLotCreated(),
                reactionCreatedEvent.getParkingLotUuid(),
                reactionCreatedEvent
        );
    }
}
