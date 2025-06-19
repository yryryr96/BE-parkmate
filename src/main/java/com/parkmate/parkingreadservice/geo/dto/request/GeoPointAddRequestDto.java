package com.parkmate.parkingreadservice.geo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoPointAddRequestDto {

    private String parkingLotUuid;
    private double latitude;
    private double longitude;

    @Builder
    private GeoPointAddRequestDto(String parkingLotUuid,
                                 double latitude,
                                 double longitude) {
        this.parkingLotUuid = parkingLotUuid;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
