package com.parkmate.parkingreadservice.geo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InBoxParkingLotRequestDto {

    private double swLat;
    private double swLng;
    private double neLat;
    private double neLng;

    @Builder
    private InBoxParkingLotRequestDto(double swLat,
                                     double swLng,
                                     double neLat,
                                     double neLng) {
        this.swLat = swLat;
        this.swLng = swLng;
        this.neLat = neLat;
        this.neLng = neLng;
    }

    public static InBoxParkingLotRequestDto of(double swLat, double swLng, double neLat, double neLng) {
        return new InBoxParkingLotRequestDto(swLat, swLng, neLat, neLng);
    }
}
