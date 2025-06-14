package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.NearbyParkingLotResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NearbyParkingLotResponseDto {

    private String name;
    private String thumbnailUrl;
    private double latitude;
    private double longitude;
    private double distance;

    @Builder
    private NearbyParkingLotResponseDto(String name,
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

    public static NearbyParkingLotResponseDto of(ParkingLotReadResponseDto parkingLotReadResponseDto,
                            double latitude,
                            double longitude,
                            double distance) {

        return NearbyParkingLotResponseDto.builder()
                .name(parkingLotReadResponseDto.getName())
//                .thumbnailUrl(parkingLotReadResponseDto.getThumbnailUrl())
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .build();
    }

    public NearbyParkingLotResponseVo toVo() {
        return NearbyParkingLotResponseVo.builder()
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .build();
    }

}
