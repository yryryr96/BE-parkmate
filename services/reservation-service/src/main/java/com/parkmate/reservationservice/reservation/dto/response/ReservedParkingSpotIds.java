package com.parkmate.reservationservice.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ReservedParkingSpotIds {

    private Set<Long> parkingSpotIds;

    @Builder
    private ReservedParkingSpotIds(Set<Long> parkingSpotIds) {
        this.parkingSpotIds = parkingSpotIds;
    }

    public static ReservedParkingSpotIds of(Set<Long> parkingSpotIds) {
        return ReservedParkingSpotIds.builder()
                .parkingSpotIds(parkingSpotIds)
                .build();
    }
}
