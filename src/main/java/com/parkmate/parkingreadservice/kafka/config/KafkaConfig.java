package com.parkmate.parkingreadservice.kafka.config;

import com.parkmate.parkingreadservice.kafka.properties.KafkaConsumerGroupProperties;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {



    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    private Map<String, Object> getStringObjectMap(String groupId) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  //메시지 오프셋 설정
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 5000); // 재연결 시도 간격 5초
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 60000); //

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotCreateEvent> parkingLotCreateListener() {
        return createListenerFactory(ParkingLotCreateEvent.class, "parking-lot-created-group");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotMetadataUpdateEvent> parkingLotMetadataUpdateListener() {
        return createListenerFactory(ParkingLotMetadataUpdateEvent.class, "parking-lot-metadata-updated-group");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateListener() {
        ConcurrentKafkaListenerContainerFactory<String, ParkingLotReactionsUpdateEvent> factory =
                createListenerFactory(ParkingLotReactionsUpdateEvent.class, "parking-lot-reactions-updated-group");

        factory.setBatchListener(true);
        return factory;
    }

    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> targetClass, String groupId) {
        Map<String, Object> props = getStringObjectMap(groupId);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(targetClass, false))
        );
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerFactory(Class<T> targetClass, String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory(targetClass, groupId));
        return factory;
    }
}
