package com.parkmate.notificationservice.kafka.config;

import com.parkmate.notificationservice.kafka.constant.KafkaConsumerGroups;
import com.parkmate.notificationservice.notification.event.reservation.ReservationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ReservationKafkaConfig extends CommonKafkaConfig {


    @Override
    Map<String, Object> getStringObjectMap(String groupId) {
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
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 60000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1024 * 1024);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> reservationCreatedContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> factory = createListenerFactory(
                ReservationEvent.class, KafkaConsumerGroups.RESERVATION_CREATED_GROUP);

        factory.setBatchListener(true);
        factory.setConcurrency(3);
        return factory;
    }
}
