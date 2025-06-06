package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotRegisterRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotHostUuidResponseDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotResponseDto;

public interface ParkingLotService {

    ParkingLot register(ParkingLotRegisterRequestDto parkingLotRegisterRequestDto);

    void update(ParkingLotUpdateRequestDto parkingLotUpdateRequestDto);

    void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto);

    ParkingLotResponseDto findByUuid(String parkingLotUuid);

    ParkingLotHostUuidResponseDto getHostUuidByParkingLotUuid(String parkingLotUuid);
}
