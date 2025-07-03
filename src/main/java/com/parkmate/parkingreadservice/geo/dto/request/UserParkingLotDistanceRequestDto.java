package com.parkmate.parkingreadservice.geo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserParkingLotDistanceRequestDto {

    private String parkingLotUuid;
    private double latitude;
    private double longitude;

    @Builder
    private UserParkingLotDistanceRequestDto(String parkingLotUuid, double latitude, double longitude) {
        this.parkingLotUuid = parkingLotUuid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static UserParkingLotDistanceRequestDto of(String parkingLotUuid, double latitude, double longitude) {
        return UserParkingLotDistanceRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
