package com.parkmate.parkingservice.facade.parkingspot;

import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotClientRequest;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotClientResponse;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;

    public ParkingSpotClientResponse getParkingSpotsByParkingLotUuidAndType(ParkingSpotClientRequest parkingSpotClientRequest) {

        String parkingLotUuid = parkingSpotClientRequest.getParkingLotUuid();
        List<ParkingSpotSimpleResponseDto> parkingSpots = parkingSpotService.getParkingSpotsByParkingLotUuidAndType(
                parkingLotUuid,
                parkingSpotClientRequest.getParkingSpotType()
        );

        String hostUuid = parkingLotService.findByUuid(parkingLotUuid).getHostUuid();

        return ParkingSpotClientResponse.of(
                hostUuid,
                parkingLotUuid,
                parkingSpots
        );
    }
}
