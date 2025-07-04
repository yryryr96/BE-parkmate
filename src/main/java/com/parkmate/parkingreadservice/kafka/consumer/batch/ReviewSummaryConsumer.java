package com.parkmate.parkingreadservice.kafka.consumer.batch;

import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import com.parkmate.parkingreadservice.kafka.event.review.ReviewSummaryUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReviewSummaryConsumer {

    private final ParkingLotReadService parkingLotReadService;

    @KafkaListener(
            topics = KafkaTopics.REVIEW_SUMMARY_UPDATED,
            containerFactory = "reviewSummaryUpdateKafkaListener"
    )
    public void consumeReviewSummaryUpdate(List<ReviewSummaryUpdateEvent> events) {

        log.info("Received ReviewSummaryUpdateEvent size : {}", events.size());
        parkingLotReadService.bulkUpdateRating(events);
    }
}
