package com.parkmate.parkingreadservice.kafka.consumer.operation;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.kafka.eventmanager.OperationEventManager;
import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParkingLotOperationConsumer {

    private final OperationEventManager eventManager;

    @KafkaListener(
            topics = KafkaTopics.parkingLotOperationCreated,
            containerFactory = "parkingLotOperationCreatedListener"
    )
    public void consumeOperationCreatedEvent(OperationCreateEvent event) {
        eventManager.handleOperationCreatedEvent(event);
    }
}
