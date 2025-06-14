package com.parkmate.parkingreadservice.kafka.consumer.parkinglot;

import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.eventmanager.ParkingLotEventManager;
import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParkingLotReadConsumer {

    private final ParkingLotEventManager eventManager;

    @KafkaListener(
            topics = KafkaTopics.parkingLotCreated,
            containerFactory = "parkingLotCreateListener",
            concurrency = "3"
    )
    public void consumeParkingLotCreated(ParkingLotCreateEvent event) {
        eventManager.handleParkingLotCreatedEvent(event);
    }

    @KafkaListener(
            topics = KafkaTopics.parkingLotMetadataUpdated,
            containerFactory = "parkingLotMetadataUpdateListener"
    )
    public void consumeParkingLotMetadataUpdated(ParkingLotMetadataUpdateEvent event) {
        eventManager.handleParkingLotMetadataUpdatedEvent(event);
    }

    @KafkaListener(
            topics = KafkaTopics.parkingLotReactionsUpdated,
            containerFactory = "parkingLotReactionsUpdateListener",
            concurrency = "3"
    )
    public void consumeParkingLotReactionsUpdated(List<ParkingLotReactionsUpdateEvent> events) {
        eventManager.handleParkingLotReactionsUpdatedEvent(events);
    }
}
