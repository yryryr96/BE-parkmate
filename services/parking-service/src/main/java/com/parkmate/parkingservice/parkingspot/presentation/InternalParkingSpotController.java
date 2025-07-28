package com.parkmate.parkingservice.parkingspot.presentation;

import com.parkmate.parkingservice.facade.parkingspot.ParkingSpotFacade;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotClientRequest;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotClientResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "RESERVATION-SERVICE에서 주차 공간 정보 조회",
            description = "주차 공간 정보를 조회하는 API입니다. " +
                          "주차장 UUID와 주차 공간 타입을 기반으로 주차 공간 정보를 반환합니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @PostMapping("/search")
    public ParkingSpotClientResponse getParkingLotsSimpleInfo(@RequestBody ParkingSpotClientRequest parkingSpotClientRequest) {
        return parkingSpotFacade.getParkingSpotsByParkingLotUuidAndType(parkingSpotClientRequest);
    }

}
