package com.parkmate.parkingreadservice.parkinglotread.consumer;

import com.parkmate.parkingreadservice.common.properties.KafkaTopicProperties;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.parkinglotread.infrastructure.ParkingLotReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParkingLotReadConsumer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final ParkingLotReadRepository parkingLotReadRepository;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.parkingLotCreated}",
            containerFactory = "parkingLotCreateListener"
    )
    public void consumeParkingLotCreated(ParkingLotCreateEvent parkingLotCreateEvent) {
        log.info("Received message from {} topic: {}",
                kafkaTopicProperties.getParkingLotCreated(),
                parkingLotCreateEvent.toString());

        parkingLotReadRepository.save(parkingLotCreateEvent.toEntity());
    }
}
