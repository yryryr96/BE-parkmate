package com.parkmate.parkingreadservice.kafka.consumer.parkinglot;

import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.eventmanager.ParkingLotEventManager;
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
            topics = KafkaTopics.PARKING_LOT_CREATED,
            containerFactory = "parkingLotCreateListener",
            concurrency = "3"
    )
    public void consumeParkingLotCreated(ParkingLotCreateEvent event) {
        eventManager.handleParkingLotCreatedEvent(event);
    }

    @KafkaListener(
            topics = KafkaTopics.PARKING_LOT_METADATA_UPDATED,
            containerFactory = "parkingLotMetadataUpdateListener"
    )
    public void consumeParkingLotMetadataUpdated(ParkingLotMetadataUpdateEvent event) {
        eventManager.handleParkingLotMetadataUpdatedEvent(event);
    }

    @KafkaListener(
            topics = KafkaTopics.PARKING_LOT_REACTIONS_UPDATED,
            containerFactory = "parkingLotReactionsUpdateListener",
            concurrency = "3"
    )
    public void consumeParkingLotReactionsUpdated(List<ParkingLotReactionsUpdateEvent> events) {
        eventManager.handleParkingLotReactionsUpdatedEvent(events);
    }
}
