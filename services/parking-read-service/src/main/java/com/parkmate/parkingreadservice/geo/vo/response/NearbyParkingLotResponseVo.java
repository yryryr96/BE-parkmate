package com.parkmate.parkingreadservice.geo.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NearbyParkingLotResponseVo {

    private String name;
    private String thumbnailUrl;
    private double latitude;
    private double longitude;
    private double distance;

    @Builder
    private NearbyParkingLotResponseVo(String name,
                                       String thumbnailUrl,
                                       double latitude,
                                       double longitude,
                                       double distance) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
