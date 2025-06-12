package com.parkmate.parkingservice.kafka.producer.parkinglot;

import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.kafka.event.ReactionUpdatedEvent;
import com.parkmate.parkingservice.kafka.properties.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParkingLotProducer {

    private final KafkaTemplate<String, ReactionUpdatedEvent> parkingLotReactionsKafkaTemplate;
    private final KafkaTemplate<String, ParkingLotCreatedEvent> parkingLotCreatedKafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReactionUpdatedEvent(ReactionUpdatedEvent reactionUpdatedEvent) {
        parkingLotReactionsKafkaTemplate.send(
                kafkaTopicProperties.getParkingLotReactionsUpdated(),
                reactionUpdatedEvent.getParkingLotUuid(),
                reactionUpdatedEvent
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendParkingLotCreatedEvent(ParkingLotCreatedEvent parkingLotCreatedEvent) {
        parkingLotCreatedKafkaTemplate.send(
                kafkaTopicProperties.getParkingLotCreated(),
                parkingLotCreatedEvent.getParkingLotUuid(),
                parkingLotCreatedEvent
        );
    }
}
