package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;

public interface CustomMongoRepository {

    void updateParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void updateParkingLotReactions(ParkingLotReactionsUpdateEvent parkingLotReactionsUpdateEvent);
}
