package com.parkmate.parkingreadservice.kafka.event.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSummaryUpdateEvent {

    private String parkingLotUuid;
    private double averageRating;
    private int totalReview;
}
