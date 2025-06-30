package com.parkmate.reservationservice.kafka.config.produce;

import com.parkmate.reservationservice.kafka.event.ReservationCreateEvent;
import com.parkmate.reservationservice.kafka.constant.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProduceConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> reservationProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024 * 1024);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        return props;
    }

    @Bean
    public NewTopic reservationCreatedTopic() {
        return TopicBuilder
                .name(KafkaTopics.RESERVATION_CREATED)
                .partitions(3)
                .replicas(2)
                .build();
    }

    @Bean
    public ProducerFactory<String, ReservationCreateEvent> createReservationNotification() {
        return new DefaultKafkaProducerFactory<>(reservationProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, ReservationCreateEvent> kafkaTemplate() {
        return new KafkaTemplate<>(createReservationNotification());
    }
}
