package com.parkmate.parkingreadservice.parkinglotread.consumer;

import com.parkmate.parkingreadservice.common.properties.KafkaTopicProperties;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
            containerFactory = "parkingLotReactionsUpdateListener"
    )
    public void consumeParkingLotReactionsUpdated(ParkingLotReactionsUpdateEvent parkingLotReactionsUpdateEvent) {
        parkingLotReadService.syncParkingLotReactions(parkingLotReactionsUpdateEvent);
    }
}
