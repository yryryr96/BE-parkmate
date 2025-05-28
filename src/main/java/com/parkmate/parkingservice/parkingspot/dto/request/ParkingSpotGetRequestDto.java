package com.parkmate.parkingservice.parkingspot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotGetRequestDto {

    private Long parkingSpotId;
    private String parkingLotUuid;

    @Builder
    private ParkingSpotGetRequestDto(Long parkingSpotId,
                                    String parkingLotUuid) {
        this.parkingSpotId = parkingSpotId;
        this.parkingLotUuid = parkingLotUuid;
    }

    public static ParkingSpotGetRequestDto of(String parkingLotUuid,
                                              Long parkingSpotId) {
        return ParkingSpotGetRequestDto.builder()
                .parkingSpotId(parkingSpotId)
                .parkingLotUuid(parkingLotUuid)
                .build();
    }

}
