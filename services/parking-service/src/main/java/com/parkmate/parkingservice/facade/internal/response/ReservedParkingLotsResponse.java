package com.parkmate.parkingservice.facade.internal.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
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

    public static ReservedParkingLotsResponse of(List<ParkingLot> parkingLots, List<ParkingSpot> parkingSpots) {

        List<ReservedParkingLotSimpleResponse> lotResponses = parkingLots.stream()
                .map(ReservedParkingLotSimpleResponse::from)
                .toList();

        List<ReservedParkingSpotSimpleResponse> spotResponses = parkingSpots.stream()
                .map(ReservedParkingSpotSimpleResponse::from)
                .toList();

        return ReservedParkingLotsResponse.builder()
                .parkingLots(lotResponses)
                .parkingSpots(spotResponses)
                .build();
    }
}
