package com.parkmate.parking_service.parkinglot.application;

import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotCreateRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parking_service.parkinglot.dto.response.ParkingLotResponseDto;

public interface ParkingLotService {

    void register(ParkingLotCreateRequestDto parkingLotCreateRequestDto);
    void update(ParkingLotUpdateRequestDto parkingLotUpdateRequestDto);
    void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto);

    ParkingLotResponseDto findByUuid(String parkingLotUuid);
}
