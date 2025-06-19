package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservedParkingLotsResponse {

    private List<ReservedParkingLotSimpleResponse> parkingLots;
    private List<ReservedParkingSpotSimpleResponse> parkingSpots;

    @Builder
    private ReservedParkingLotsResponse(List<ReservedParkingLotSimpleResponse> parkingLots,
                                       List<ReservedParkingSpotSimpleResponse> parkingSpots) {
        this.parkingLots = parkingLots;
        this.parkingSpots = parkingSpots;
    }
}
