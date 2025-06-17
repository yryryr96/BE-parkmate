package com.parkmate.parkingservice.facade.internal.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingLotRequest {

    private String parkingLotUuid;
    private Long parkingSpotId;

    @Builder
    private ReservedParkingLotRequest(String parkingLotUuid,
                                     Long parkingSpotId) {
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
    }

    public static ReservedParkingLotRequest of(String parkingLotUuid, Long parkingSpotId) {
        return ReservedParkingLotRequest.builder()
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotId(parkingSpotId)
                .build();
    }
}
