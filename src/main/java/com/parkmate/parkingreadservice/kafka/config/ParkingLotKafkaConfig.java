package com.parkmate.parkingreadservice.kafka.config;

import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.constant.KafkaConsumerGroups;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class ParkingLotKafkaConfig extends CommonKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotCreateEvent> parkingLotCreateListener() {
        return createListenerFactory(ParkingLotCreateEvent.class, KafkaConsumerGroups.PARKING_LOT_CREATED_GROUP);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotMetadataUpdateEvent> parkingLotMetadataUpdateListener() {
        return createListenerFactory(ParkingLotMetadataUpdateEvent.class, KafkaConsumerGroups.PARKING_LOT_METADATA_UPDATED_GROUP);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateListener() {
        ConcurrentKafkaListenerContainerFactory<String, ParkingLotReactionsUpdateEvent> factory =
                createListenerFactory(ParkingLotReactionsUpdateEvent.class, KafkaConsumerGroups.PARKING_LOT_REACTIONS_UPDATED_GROUP);

        factory.setBatchListener(true);
        return factory;
    }
}
