package com.parkmate.parkingservice.facade;

import com.parkmate.parkingservice.facade.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotGeoLocationServiceImpl;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.application.GeoService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotGeoLocation;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglotimagemapping.application.ParkingLotImageMappingService;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final ParkingLotImageMappingService parkingLotImageMappingService;
    private final GeoService geoService;
    private final ParkingLotGeoLocationServiceImpl parkingLotGeoLocationService;

    @Transactional
    public void registerParkingLot(ParkingLotRegisterRequest parkingLotRegisterRequest) {

        ParkingLot parkingLot = parkingLotService.register(parkingLotRegisterRequest.getParkingLot());
        String parkingLotUuid = parkingLot.getParkingLotUuid();

        Optional.ofNullable(parkingLotRegisterRequest.getParkingSpot())
                .ifPresent(parkingSpot ->
                        parkingSpotService.register(parkingSpot.withParkingLotUuid(parkingLotUuid))
                );

        Optional.ofNullable(parkingLotRegisterRequest.getParkingLotImage())
                .ifPresent(parkingLotImage ->
                        parkingLotImageMappingService.registerParkingLotImages(parkingLotImage.withParkingLotUuid(parkingLotUuid))
                );

        geoService.addParkingLot(
                parkingLot.getParkingLotUuid(),
                parkingLot.getLatitude(),
                parkingLot.getLongitude()
        );
    }

    public List<ParkingLotGeoResponseDto> getNearbyParkingLotsRedis(double latitude,
                                                                    double longitude,
                                                                    double radius) {

        return geoService.getNearbyParkingLots(latitude, longitude, radius);
    }

    public List<ParkingLotGeoResponseDto> getNearbyParkingLotsMysql(double latitude,
                                                                double longitude,
                                                                double radius) {

        return parkingLotGeoLocationService.getNearbyParkingLots(latitude, longitude, radius);
    }
}
