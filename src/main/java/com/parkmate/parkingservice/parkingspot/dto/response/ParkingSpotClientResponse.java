package com.parkmate.parkingservice.parkingspot.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingSpotClientResponse {

    private String hostUuid;
    private String parkingLotUuid;
    private List<ParkingSpotSimpleResponseDto> parkingSpots;

    @Builder
    private ParkingSpotClientResponse(String hostUuid,
                                      String parkingLotUuid,
                                      List<ParkingSpotSimpleResponseDto> parkingSpots) {
        this.hostUuid = hostUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpots = parkingSpots;
    }

    public static ParkingSpotClientResponse of(String hostUuid,
                                               String parkingLotUuid,
                                               List<ParkingSpotSimpleResponseDto> parkingSpots) {
        return ParkingSpotClientResponse.builder()
                .hostUuid(hostUuid)
                .parkingLotUuid(parkingLotUuid)
                .parkingSpots(parkingSpots)
                .build();
    }
}
