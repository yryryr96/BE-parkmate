package com.parkmate.parkingservice.kafka.producer.operation;

import com.parkmate.parkingservice.kafka.event.OperationCreatedEvent;
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
public class OperationProducer {

    private final KafkaTemplate<String, OperationCreatedEvent> operationCreatedKafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOperationCreatedEvent(OperationCreatedEvent operationCreatedEvent) {
        operationCreatedKafkaTemplate.send(
                KafkaTopics.parkingLotOperationCreated,
                operationCreatedEvent.getParkingLotUuid(),
                operationCreatedEvent
        );
        log.info("Sent OperationCreatedEvent for parking lot: {}", operationCreatedEvent.getParkingLotUuid());
    }
}
