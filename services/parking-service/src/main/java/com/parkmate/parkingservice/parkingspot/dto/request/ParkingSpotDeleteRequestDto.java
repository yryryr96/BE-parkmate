package com.parkmate.parkingservice.parkingspot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotDeleteRequestDto {

    private String parkingLotUuid;
    private Long parkingSpotId;

    @Builder
    private ParkingSpotDeleteRequestDto(String parkingLotUuid,
                                       Long parkingSpotId) {
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
    }

    public static ParkingSpotDeleteRequestDto of(String parkingLotUuid,
                                                 Long parkingSpotId) {

        return ParkingSpotDeleteRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotId(parkingSpotId)
                .build();
    }
}
