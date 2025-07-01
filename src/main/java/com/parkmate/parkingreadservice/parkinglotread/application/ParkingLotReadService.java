package com.parkmate.parkingreadservice.parkinglotread.application;

import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadSimpleResponseDto;

import java.util.List;

public interface ParkingLotReadService {

    void createParkingLot(ParkingLotCreateEvent parkingLotCreateEvent);

    ParkingLotReadResponseDto getParkingLotReadByParkingLotUuid(String parkingLotUuid);

    void syncParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void syncParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEventList);

    List<ParkingLotReadResponseDto> getParkingLotsByUuids(List<String> parkingLotUuids);

    List<ParkingLotRead> findAll();

    ParkingLotReadSimpleResponseDto getParkingLotReadSimpleByParkingLotUuid(String parkingLotUuid);
}
