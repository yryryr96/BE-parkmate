package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingLotResponse {

    private String thumbnailUrl;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;

    @Builder
    private ReservedParkingLotResponse(String thumbnailUrl,
                                       String parkingLotUuid,
                                       String parkingLotName,
                                       Long parkingSpotId,
                                       String parkingSpotName) {
        this.thumbnailUrl = thumbnailUrl;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
    }
}
