package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;

public interface CustomMongoRepository {

    void updateParkingLotMetadata(ParkingLotMetadataUpdateEvent event);
}
