package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingLotSimpleResponse {

    private String thumbnailUrl;
    private String parkingLotUuid;
    private String parkingLotName;

    @Builder
    private ReservedParkingLotSimpleResponse(String thumbnailUrl,
                                             String parkingLotUuid,
                                             String parkingLotName) {
        this.thumbnailUrl = thumbnailUrl;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
    }
}
