package com.parkmate.parkingservice.facade;

import com.parkmate.parkingservice.facade.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.parkinglot.application.GeoServiceImplByMysql;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.application.GeoService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglotimagemapping.application.ParkingLotImageMappingService;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void registerParkingLot(ParkingLotRegisterRequest parkingLotRegisterRequest) {

        ParkingLot parkingLot = parkingLotService.register(parkingLotRegisterRequest.getParkingLot());
        String parkingLotUuid = parkingLot.getParkingLotUuid();

        List<ParkingSpotResponseDto> registeredParkingSpots = Optional.ofNullable(parkingLotRegisterRequest.getParkingSpot())
                .map(parkingSpot ->
                        parkingSpotService.register(parkingSpot.withParkingLotUuid(parkingLotUuid))
                ).orElse(null);

        List<ParkingLotImageMappingResponseDto> registeredImages = Optional.ofNullable(parkingLotRegisterRequest.getParkingLotImage())
                .map(parkingLotImage ->
                        parkingLotImageMappingService.registerParkingLotImages(parkingLotImage.withParkingLotUuid(parkingLotUuid))
                ).orElse(null);

        geoService.addParkingLot(
                parkingLot.getParkingLotUuid(),
                parkingLot.getLatitude(),
                parkingLot.getLongitude()
        );

        eventPublisher.publishEvent(
                ParkingLotCreatedEvent.of(parkingLot, registeredParkingSpots, registeredImages)
        );
    }

    public List<ParkingLotGeoResponseDto> getNearbyParkingLotsMysql(double latitude,
                                                                double longitude,
                                                                double radius) {

        return geoService.getNearbyParkingLots(latitude, longitude, radius);
    }
}
