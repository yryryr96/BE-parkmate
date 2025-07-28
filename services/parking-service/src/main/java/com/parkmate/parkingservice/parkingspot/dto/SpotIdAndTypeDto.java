package com.parkmate.parkingservice.parkingspot.dto;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpotIdAndTypeDto {

    private Long parkingSpotId;
    private ParkingSpotType type;

    @Builder
    private SpotIdAndTypeDto(Long parkingSpotId, ParkingSpotType type) {
        this.parkingSpotId = parkingSpotId;
        this.type = type;
    }
}
