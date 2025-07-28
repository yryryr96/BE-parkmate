package com.parkmate.parkingservice.parkingspot.dto.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingSpotClientResponse {

    private String hostUuid;
    private String parkingLotUuid;
    private String parkingLotName;
    private List<ParkingSpotSimpleResponseDto> parkingSpots;

    @Builder
    private ParkingSpotClientResponse(String hostUuid,
                                      String parkingLotUuid,
                                      String parkingLotName,
                                      List<ParkingSpotSimpleResponseDto> parkingSpots) {
        this.hostUuid = hostUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpots = parkingSpots;
    }

    public static ParkingSpotClientResponse of(ParkingLot parkingLot,
                                               List<ParkingSpotSimpleResponseDto> parkingSpots) {
        return ParkingSpotClientResponse.builder()
                .hostUuid(parkingLot.getHostUuid())
                .parkingLotName(parkingLot.getName())
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .parkingSpots(parkingSpots)
                .build();
    }
}
