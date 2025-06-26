package com.parkmate.userparkinghistoryservice.kafka.config;

import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import com.parkmate.userparkinghistoryservice.kafka.utils.CustomSerializer;
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

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> historyProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return props;
    }

    @Bean
    public NewTopic userEntryTopic() {
        return TopicBuilder.name("user-parking-history.history")
                .partitions(3)
                .replicas(2)
                .build();
    }

    @Bean
    public KafkaTemplate<String, HistoryEvent> historyKafkaTemplate() {
        return new KafkaTemplate<>(historyFactory());
    }

    @Bean
    public ProducerFactory<String, HistoryEvent> historyFactory() {
        return new DefaultKafkaProducerFactory<>(historyProducerConfigs());
    }
}
