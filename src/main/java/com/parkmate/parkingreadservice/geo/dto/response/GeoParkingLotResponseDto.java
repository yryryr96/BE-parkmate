package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.GeoParkingLotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoParkingLotResponseDto {

    private String parkingLotUuid;
    private String name;
    private String thumbnailUrl;
    private double longitude;
    private double latitude;
    private double distance;

    @Builder
    private GeoParkingLotResponseDto(String parkingLotUuid,
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

    public GeoParkingLotResponseVo toVo() {
        return GeoParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .longitude(longitude)
                .latitude(latitude)
                .distance(distance)
                .build();
    }
}
