package com.parkmate.parkingservice.parkinglot.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotGeoResponseVo {

    private String parkingLotUuid;
    private double longitude;
    private double latitude;
    private double distance;

    @Builder
    private ParkingLotGeoResponseVo(String parkingLotUuid,
                                   double longitude,
                                   double latitude,
                                   double distance) {
        this.parkingLotUuid = parkingLotUuid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }
}
