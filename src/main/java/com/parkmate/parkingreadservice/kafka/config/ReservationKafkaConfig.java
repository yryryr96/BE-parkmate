package com.parkmate.parkingreadservice.kafka.config;

import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;
import com.parkmate.parkingreadservice.kafka.constant.KafkaConsumerGroups;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class ReservationKafkaConfig extends CommonKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationCreateEvent> reservationCreateKafkaListener() {
        return createListenerFactory(ReservationCreateEvent.class, KafkaConsumerGroups.RESERVATION_CREATED_GROUP);
    }
}
