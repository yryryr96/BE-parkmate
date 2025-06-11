package com.parkmate.parkingservice.parkinglot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NearbyParkingLotResponseDto {

    private String parkingLotUuid;
    private String name;
    private String thumbnailUrl;
    private double longitude;
    private double latitude;
    private double distance;
}
