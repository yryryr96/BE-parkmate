package com.parkmate.parkingreadservice.geo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoSearchResult {

    private String parkingLotUuid;
    private double longitude;
    private double latitude;
    private double distance;

    @Builder
    private GeoSearchResult(String parkingLotUuid,
                            double longitude,
                            double latitude,
                            double distance) {
        this.parkingLotUuid = parkingLotUuid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }
}
