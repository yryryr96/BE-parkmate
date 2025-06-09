package com.parkmate.parkingservice.kafka.config;

import com.parkmate.parkingservice.common.utils.CustomSerializer;
import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.kafka.event.ReactionUpdatedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.producer.acks}")
    private String ACKS_CONFIG_VALUE;

    @Value("${spring.kafka.producer.linger-ms}")
    private int LINGER_MS_VALUE;


    @Bean
    public Map<String, Object> parkingLotProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG_VALUE);
        props.put(ProducerConfig.LINGER_MS_CONFIG, LINGER_MS_VALUE);
        return props;
    }

    @Bean
    public ProducerFactory<String, ReactionUpdatedEvent> createParkingLotReactionsFactory() {
        return new DefaultKafkaProducerFactory<>(parkingLotProducerConfigs());
    }

    @Bean(name = "parkingLotReactionsKafkaTemplate")
    public KafkaTemplate<String, ReactionUpdatedEvent> parkingLotReactionsKafkaTemplate() {
        return new KafkaTemplate<>(createParkingLotReactionsFactory());
    }

    @Bean
    public ProducerFactory<String, ParkingLotCreatedEvent> parkingLotCreatedFactory() {
        return new DefaultKafkaProducerFactory<>(parkingLotProducerConfigs());
    }

    @Bean(name = "parkingLotCreatedKafkaTemplate")
    public KafkaTemplate<String, ParkingLotCreatedEvent> parkingLotCreatedKafkaTemplate() {
        return new KafkaTemplate<>(parkingLotCreatedFactory());
    }
}
