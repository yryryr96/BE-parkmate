package com.parkmate.reservationservice.kafka.config.consume;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public abstract class CommonConsumeKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    protected String bootstrapServer;

    abstract Map<String, Object> getStringObjectMap(String groupId);

    protected <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> targetClass, String groupId) {
        Map<String, Object> props = getStringObjectMap(groupId);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(targetClass, false))
        );
    }

    protected <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerFactory(Class<T> targetClass, String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory(targetClass, groupId));
        return factory;
    }
}
