package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;

import java.util.List;

public interface GeoService {

    void addParkingLot(String parkingLotUuid, double latitude, double longitude);

    List<ParkingLotGeoResponseDto> getNearbyParkingLots(double latitude, double longitude, double radius);
}
