package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglot.infrastructure.ParkingLotGeoLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoServiceImplByMysql implements GeoService {

    private final ParkingLotGeoLocationRepository parkingLotGeoLocationRepository;

    @Transactional
    @Override
    public void addParkingLot(String parkingLotUuid,
                              double latitude,
                              double longitude) {

        parkingLotGeoLocationRepository.add(parkingLotUuid, latitude, longitude);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingLotGeoResponseDto> getNearbyParkingLots(double latitude, double longitude, double radius) {
        return parkingLotGeoLocationRepository.findNearbyParkingLots(latitude, longitude, radius);
    }
}
