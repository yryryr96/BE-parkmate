package com.parkmate.parkingservice.facade;

import com.parkmate.parkingservice.facade.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglotimagemapping.application.ParkingLotImageMappingService;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final ParkingLotImageMappingService parkingLotImageMappingService;

    @Transactional
    public void registerParkingLot(ParkingLotRegisterRequest parkingLotRegisterRequest) {

        ParkingLot parkingLot = parkingLotService.register(parkingLotRegisterRequest.getParkingLot());
        String parkingLotUuid = parkingLot.getParkingLotUuid();

        parkingSpotService.register(parkingLotRegisterRequest.getParkingSpot().withParkingLotUuid(parkingLotUuid));

        parkingLotImageMappingService.registerParkingLotImages(parkingLotRegisterRequest.getParkingLotImage().withParkingLotUuid(parkingLotUuid));
    }
}
