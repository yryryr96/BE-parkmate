package com.parkmate.parkingservice.parkingspot.dto.response;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotSimpleResponseDto {

    private Long id;
    private String name;

    @Builder
    private ParkingSpotSimpleResponseDto(Long id,
                                        String name) {
        this.id = id;
        this.name = name;
    }

    public static ParkingSpotSimpleResponseDto from(ParkingSpot parkingSpot) {
        return ParkingSpotSimpleResponseDto.builder()
                .id(parkingSpot.getId())
                .name(parkingSpot.getName())
                .build();
    }
}
