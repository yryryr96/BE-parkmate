package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.InBoxParkingLotResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseDto {

    private String parkingLotUuid;
    private String name;
    private String thumbnailUrl;
    private double latitude;
    private double longitude;
    private double distance;
    private int availableSpotCount;

    @Builder
    private InBoxParkingLotResponseDto(String parkingLotUuid,
                                       String name,
                                       String thumbnailUrl,
                                       double latitude,
                                       double longitude,
                                       double distance,
                                       int availableSpotCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.availableSpotCount = availableSpotCount;
    }

    public static InBoxParkingLotResponseDto of(ParkingLotReadResponseDto parkingLotReadResponseDto,
                                                double latitude,
                                                double longitude,
                                                double distance) {
        return InBoxParkingLotResponseDto.builder()
                .parkingLotUuid(parkingLotReadResponseDto.getParkingLotUuid())
                .name(parkingLotReadResponseDto.getName())
//                .thumbnailUrl(parkingLotReadResponseDto.getThumbnailUrl())
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .availableSpotCount(parkingLotReadResponseDto.getCapacity())
                .build();
    }

    public InBoxParkingLotResponseVo toVo() {
        return InBoxParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .availableSpotCount(availableSpotCount)
                .build();
    }
}
