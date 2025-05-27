package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotCreateRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotResponseDto;

public interface ParkingLotService {

    void register(ParkingLotCreateRequestDto parkingLotCreateRequestDto);

    void update(ParkingLotUpdateRequestDto parkingLotUpdateRequestDto);

    void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto);

    ParkingLotResponseDto findByUuid(String parkingLotUuid);
}
