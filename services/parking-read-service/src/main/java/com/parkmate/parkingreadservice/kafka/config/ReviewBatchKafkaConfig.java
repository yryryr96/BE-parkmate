package com.parkmate.parkingreadservice.kafka.config;

import com.parkmate.parkingreadservice.kafka.constant.KafkaConsumerGroups;
import com.parkmate.parkingreadservice.kafka.event.review.ReviewSummaryUpdateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class ReviewBatchKafkaConfig extends CommonKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReviewSummaryUpdateEvent> reviewSummaryUpdateKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewSummaryUpdateEvent> factory = createListenerFactory(
                ReviewSummaryUpdateEvent.class,
                KafkaConsumerGroups.REVIEW_SUMMARY_UPDATED_GROUP);

        factory.setBatchListener(true);
        return factory;
    }
}
