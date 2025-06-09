package com.parkmate.parkingreadservice.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.consumer-group")
@Getter
@Setter
public class KafkaConsumerGroupProperties {

    private String parkingLotCreatedGroup;
    private String parkingLotMetadataUpdatedGroup;
    private String parkingLotReactionsUpdatedGroup;
}
