package com.parkmate.parkingreadservice.parkinglot.consumer;

import com.parkmate.parkingreadservice.common.constant.MessageTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ParkingLotReadConsumer {

    @KafkaListener(topics = MessageTopic.PARKING_LOT_CREATED_TOPIC, groupId = "parking-lot-read-group",
    containerFactory = "kafkaListenerContainerFactory")
    public void consumeParkingLotCreated(String message) {
        log.info("Received message on topic {}: {}", MessageTopic.PARKING_LOT_CREATED_TOPIC, message);
    }
}
