package com.parkmate.parkingservice.parkinglotoptionmapping.application;

import com.parkmate.parkingservice.parkinglotoptionmapping.dto.request.ParkingLotMappingUpdateRequestDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.response.ParkingLotOptionMappingResponseDto;

public interface ParkingLotOptionMappingService {

    ParkingLotOptionMappingResponseDto update(ParkingLotMappingUpdateRequestDto parkingLotMappingUpdateRequestDto);


}
