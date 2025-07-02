package com.parkmate.parkmateorderservice.kafka.config.consume;

import com.parkmate.parkmateorderservice.kafka.constant.KafkaConsumerGroups;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentCompleteEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentKafkaConfig extends CommonConsumeKafkaConfig {

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
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 5000);
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 60000);

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCompleteEvent> paymentCompleteEventContainerFactory() {
        return createListenerFactory(PaymentCompleteEvent.class, KafkaConsumerGroups.PAYMENT_GROUP);
    }
}
