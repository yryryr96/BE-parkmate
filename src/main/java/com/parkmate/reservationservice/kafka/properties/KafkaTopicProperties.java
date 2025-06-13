package com.parkmate.reservationservice.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicProperties {

    private String reservationCreated;
}
