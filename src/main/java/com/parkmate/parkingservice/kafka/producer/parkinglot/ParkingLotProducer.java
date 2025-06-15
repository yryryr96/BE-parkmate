package com.parkmate.parkingservice.kafka.producer.parkinglot;

import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.kafka.event.ReactionUpdatedEvent;
import com.parkmate.parkingservice.kafka.constant.KafkaTopics;
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReactionUpdatedEvent(ReactionUpdatedEvent reactionUpdatedEvent) {
        parkingLotReactionsKafkaTemplate.send(
                KafkaTopics.parkingLotReactionsUpdated,
                reactionUpdatedEvent.getParkingLotUuid(),
                reactionUpdatedEvent
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendParkingLotCreatedEvent(ParkingLotCreatedEvent parkingLotCreatedEvent) {
        parkingLotCreatedKafkaTemplate.send(
                KafkaTopics.parkingLotCreated,
                parkingLotCreatedEvent.getParkingLotUuid(),
                parkingLotCreatedEvent
        );
    }
}
