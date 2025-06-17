package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingSpotSimpleResponse {

    private Long id;
    private String parkingSpotName;

    @Builder
    private ReservedParkingSpotSimpleResponse(Long id,
                                             String parkingSpotName) {
        this.id = id;
        this.parkingSpotName = parkingSpotName;
    }
}
