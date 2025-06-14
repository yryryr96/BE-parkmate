package com.parkmate.parkingreadservice.geo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NearbyParkingLotRequestDto {

    private double latitude;
    private double longitude;
    private double radius;

    @Builder
    private NearbyParkingLotRequestDto(double latitude, double longitude, double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public static NearbyParkingLotRequestDto of(double latitude, double longitude, double radius) {
        return new NearbyParkingLotRequestDto(latitude, longitude, radius);
    }
}
