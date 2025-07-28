package com.parkmate.parkingservice.parkinglotimagemapping.application;

import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;

import java.util.List;

public interface ParkingLotImageMappingService {

    List<ParkingLotImageMappingResponseDto> registerParkingLotImages(ParkingLotImageRegisterRequestDto parkingLotImageRegisterRequestDto);
}
