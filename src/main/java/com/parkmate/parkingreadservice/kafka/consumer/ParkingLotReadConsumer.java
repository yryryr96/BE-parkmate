package com.parkmate.parkingreadservice.kafka.consumer;

import com.parkmate.parkingreadservice.kafka.properties.KafkaTopicProperties;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;
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
    private final ParkingLotReadService parkingLotReadService;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotCreated}",
            containerFactory = "parkingLotCreateListener"
    )
    public void consumeParkingLotCreated(ParkingLotCreateEvent parkingLotCreateEvent) {
        log.info("Received message from {} topic: {}",
                kafkaTopicProperties.getParkingLotCreated(),
                parkingLotCreateEvent.toString());
        parkingLotReadService.createParkingLot(parkingLotCreateEvent);
    }

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotMetadataUpdated}",
            containerFactory = "parkingLotMetadataUpdateListener"
    )
    public void consumeParkingLotMetadataUpdated(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent) {
        parkingLotReadService.syncParkingLotMetadata(parkingLotMetadataUpdateEvent);
    }

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotReactionsUpdated}",
            containerFactory = "parkingLotReactionsUpdateListener",
            concurrency = "3"
    )
    public void consumeParkingLotReactionsUpdated(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEvents) {
        parkingLotReadService.syncParkingLotReactions(parkingLotReactionsUpdateEvents);
    }
}
