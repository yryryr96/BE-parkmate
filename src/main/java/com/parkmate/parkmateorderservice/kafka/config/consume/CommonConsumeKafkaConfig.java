package com.parkmate.parkmateorderservice.kafka.config.consume;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Slf4j
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
        factory.setCommonErrorHandler(customErrorHandler());
        return factory;
    }


    private DefaultErrorHandler customErrorHandler() {

        return new DefaultErrorHandler((record, ex) -> {
            log.error("[Error] topic = {}, key = {}, value = {}, error message = {}",
                    record.topic(), record.key(), record.value(), ex.getMessage());
        }, new FixedBackOff(1000, 10));
    }
}
