package com.parkmate.parkingreadservice.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topics")
@Getter
@Setter
public class KafkaTopicProperties {

    private String parkingLotCreated;
    private String parkingLotMetadataUpdated;
    private String parkingLotReactionsUpdated;
    private String parkingLotOperationCreated;
}
