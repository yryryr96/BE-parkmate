package com.parkmate.parkingreadservice.kafka.consumer;

import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.eventmanager.ParkingLotEventManager;
import com.parkmate.parkingreadservice.kafka.properties.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParkingLotReadConsumer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final ParkingLotEventManager eventManager;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotCreated}",
            containerFactory = "parkingLotCreateListener",
            concurrency = "3"
    )
    public void consumeParkingLotCreated(ParkingLotCreateEvent event) {
        log.info("Received message from {} topic: {}",
                kafkaTopicProperties.getParkingLotCreated(),
                event.toString());

        eventManager.handleParkingLotCreatedEvent(event);
    }

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotMetadataUpdated}",
            containerFactory = "parkingLotMetadataUpdateListener"
    )
    public void consumeParkingLotMetadataUpdated(ParkingLotMetadataUpdateEvent event) {
        eventManager.handleParkingLotMetaDataUpdatedEvent(event);
    }

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotReactionsUpdated}",
            containerFactory = "parkingLotReactionsUpdateListener",
            concurrency = "3"
    )
    public void consumeParkingLotReactionsUpdated(List<ParkingLotReactionsUpdateEvent> events) {
        eventManager.handleParkingLotReactionsUpdatedEvent(events);
    }
}
