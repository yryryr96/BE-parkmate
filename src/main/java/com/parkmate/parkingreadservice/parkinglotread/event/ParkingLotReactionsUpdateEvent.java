package com.parkmate.parkingreadservice.parkinglotread.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotReactionsUpdateEvent {

    private String parkingLotUuid;
    private int likeCount;
    private int dislikeCount;
}
