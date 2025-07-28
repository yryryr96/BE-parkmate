package com.parkmate.parkingreadservice.kafka.config;

import com.parkmate.parkingreadservice.kafka.constant.KafkaConsumerGroups;
import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class ReservationKafkaConfig extends CommonKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> reservationCreateKafkaListener() {
        return createListenerFactory(ReservationEvent.class, KafkaConsumerGroups.RESERVATION_GROUP);
    }
}
