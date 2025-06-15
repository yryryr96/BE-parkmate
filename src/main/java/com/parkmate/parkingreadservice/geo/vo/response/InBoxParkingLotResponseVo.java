package com.parkmate.parkingreadservice.geo.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseVo {

    private String parkingLotUuid;
    private String name;
    private String thumbnailUrl;
    private double longitude;
    private double latitude;
    private double distance;
    private int availableSpotCount;

    @Builder
    private InBoxParkingLotResponseVo(String parkingLotUuid,
                                      String name,
                                      String thumbnailUrl,
                                      double longitude,
                                      double latitude,
                                      double distance,
                                      int availableSpotCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.availableSpotCount = availableSpotCount;
    }
}
