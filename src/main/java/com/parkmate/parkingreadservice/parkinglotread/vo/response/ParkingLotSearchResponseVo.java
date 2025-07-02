package com.parkmate.parkingreadservice.parkinglotread.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotSearchResponseVo {

    private String parkingLotUuid;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    @Builder
    private ParkingLotSearchResponseVo(String parkingLotUuid,
                                      String name,
                                      String address,
                                      double latitude,
                                      double longitude) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
