package com.parkmate.parkingservice.facade.internal;

import com.parkmate.parkingservice.facade.internal.request.ReservedParkingLotRequest;
import com.parkmate.parkingservice.facade.internal.response.ReservedParkingLotResponse;
import com.parkmate.parkingservice.facade.internal.response.ReservedParkingLotsResponse;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;

    @Transactional(readOnly = true)
    public ReservedParkingLotResponse getReservedParkingLot(ReservedParkingLotRequest request) {

        ParkingLot parkingLot = parkingLotService.findByUuid(request.getParkingLotUuid());
        ParkingSpot parkingSpot = parkingSpotService.findById(request.getParkingSpotId());

        return ReservedParkingLotResponse.of(parkingLot, parkingSpot);
    }

    @Transactional(readOnly = true)
    public ReservedParkingLotsResponse getReservedParkingLots(List<String> parkingLotUuids,
                                                              List<Long> parkingSpotIds) {

        List<ParkingLot> parkingLots = parkingLotService.findByUuids(parkingLotUuids);
        List<ParkingSpot> parkingSpots = parkingSpotService.findByIds(parkingSpotIds);

        return ReservedParkingLotsResponse.of(parkingLots, parkingSpots);
    }
}
