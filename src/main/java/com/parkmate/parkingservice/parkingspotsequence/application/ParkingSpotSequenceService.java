package com.parkmate.parkingservice.parkingspotsequence.application;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;

public interface ParkingSpotSequenceService {

    Long getSequenceBy(String parkingLotUuid, ParkingSpotType parkingSpotType);
}
