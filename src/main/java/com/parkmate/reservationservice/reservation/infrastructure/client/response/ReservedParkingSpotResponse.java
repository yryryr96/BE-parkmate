package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingSpotResponse {

    private String thumbnailUrl;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;
}
