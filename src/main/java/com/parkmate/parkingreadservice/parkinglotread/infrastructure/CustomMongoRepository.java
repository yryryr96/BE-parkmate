package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;

import java.util.List;

public interface CustomMongoRepository {

    void create(ParkingLotCreateEvent parkingLotCreateEvent);

    void updateParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void updateParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEvents);

    List<ParkingLotRead> findByParkingLotUuids(List<String> parkingLotUuids);
}
