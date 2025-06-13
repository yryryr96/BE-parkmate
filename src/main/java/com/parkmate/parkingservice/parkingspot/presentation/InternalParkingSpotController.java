package com.parkmate.parkingservice.parkingspot.presentation;

import com.parkmate.parkingservice.facade.parkingspot.ParkingSpotFacade;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotClientRequest;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/parkingSpots")
@RequiredArgsConstructor
public class InternalParkingSpotController {

    private final ParkingSpotFacade parkingSpotFacade;

    @PostMapping("/search")
    public ParkingSpotClientResponse getParkingLotsSimpleInfo(@RequestBody ParkingSpotClientRequest parkingSpotClientRequest) {
        return parkingSpotFacade.getParkingSpotsByParkingLotUuidAndType(parkingSpotClientRequest);
    }

}
