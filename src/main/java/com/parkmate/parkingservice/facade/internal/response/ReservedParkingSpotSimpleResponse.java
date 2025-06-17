package com.parkmate.parkingservice.facade.internal.response;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
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

    public static ReservedParkingSpotSimpleResponse from(ParkingSpot parkingSpot) {
        return ReservedParkingSpotSimpleResponse.builder()
                .id(parkingSpot.getId())
                .parkingSpotName(parkingSpot.getName())
                .build();
    }
}
