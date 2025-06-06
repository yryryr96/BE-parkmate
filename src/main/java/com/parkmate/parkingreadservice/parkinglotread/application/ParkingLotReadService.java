package com.parkmate.parkingreadservice.parkinglotread.application;

import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;

public interface ParkingLotReadService {

    void createParkingLot(ParkingLotCreateEvent parkingLotCreateEvent);

    ParkingLotReadResponseDto getParkingLotReadByParkingLotUuid(String parkingLotUuid);

    void syncParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void syncParkingLotReactions(ParkingLotReactionsUpdateEvent parkingLotReactionsUpdateEvent);
}
