package com.parkmate.parkingreadservice.geo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoParkingLotResponseVo {

    private String parkingLotUuid;
    private String name;
    private String thumbnailUrl;
    private double longitude;
    private double latitude;
    private double distance;

    @Builder
    private GeoParkingLotResponseVo(String parkingLotUuid,
                                    String name,
                                    String thumbnailUrl,
                                    double longitude,
                                    double latitude,
                                    double distance) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }
}
