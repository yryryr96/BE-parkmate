package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;

import java.util.List;

public interface GeoService {

    void addParkingLot(String parkingLotId, double latitude, double longitude);

    List<ParkingLotGeoResponseDto> getNearbyParkingLots(double latitude, double longitude, double radius);
}
