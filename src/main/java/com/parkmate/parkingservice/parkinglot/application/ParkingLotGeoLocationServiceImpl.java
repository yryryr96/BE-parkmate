package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglot.infrastructure.ParkingLotGeoLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotGeoLocationServiceImpl {

    private final ParkingLotGeoLocationRepository parkingLotGeoLocationRepository;

    @Transactional(readOnly = true)
    public List<ParkingLotGeoResponseDto> getNearbyParkingLots(double latitude, double longitude, double radius) {
        return parkingLotGeoLocationRepository.findNearbyParkingLots(latitude, longitude, radius);
    }
}
