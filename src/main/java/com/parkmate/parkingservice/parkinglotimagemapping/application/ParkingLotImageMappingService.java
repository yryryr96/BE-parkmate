package com.parkmate.parkingservice.parkinglotimagemapping.application;

import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;

import java.util.List;

public interface ParkingLotImageMappingService {

    void registerParkingLotImages(ParkingLotImageRegisterRequestDto parkingLotImageRegisterRequestDto);

    List<String> getImageUrlsByParkingLotUuid(String parkingLotUuid);
}
