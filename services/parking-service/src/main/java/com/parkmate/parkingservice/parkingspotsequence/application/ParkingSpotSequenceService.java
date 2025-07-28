package com.parkmate.parkingservice.parkingspotsequence.application;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;

public interface ParkingSpotSequenceService {

    Long getSpotSequence(String parkingLotUuid,
                         ParkingSpotType parkingSpotType
    );

    void update(
            String parkingLotUuid,
            ParkingSpotType parkingSpotType,
            Long sequence
    );
}
