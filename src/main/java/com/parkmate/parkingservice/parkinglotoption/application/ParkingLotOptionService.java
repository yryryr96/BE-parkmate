package com.parkmate.parkingservice.parkinglotoption.application;

import com.parkmate.parkingservice.parkinglotoption.dto.request.ParkingLotOptionRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDto;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDtoList;

public interface ParkingLotOptionService {

    ParkingLotOptionResponseDto registerOption(ParkingLotOptionRegisterRequestDto parkingLotOptionRegisterRequestDto);

    ParkingLotOptionResponseDtoList getOptions();
}
