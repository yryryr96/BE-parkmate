package com.parkmate.parkingreadservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSummaryUpdateEvent {

    private String parkingLotUuid;
    private double averageRating;
}
