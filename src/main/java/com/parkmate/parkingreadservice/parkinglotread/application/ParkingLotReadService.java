package com.parkmate.parkingreadservice.parkinglotread.application;

import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotCreateEvent;

public interface ParkingLotReadService {

    void createParkingLot(ParkingLotCreateEvent parkingLotCreateEvent);

    ParkingLotReadResponseDto getParkingLotReadByParkingLotUuid(String parkingLotUuid);
}
