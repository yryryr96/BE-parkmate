package com.parkmate.parkingreadservice.geo.vo.response;

import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseVo {

    private String parkingLotUuid;
    private String name;
    private String address;
    private String thumbnailUrl;
    private List<Image> imageUrls;
    private double latitude;
    private double longitude;
    private double distance;
    private int availableSpotCount;

    @Builder
    private InBoxParkingLotResponseVo(String parkingLotUuid,
                                      String name,
                                      String address,
                                      String thumbnailUrl,
                                      List<Image> imageUrls,
                                      double longitude,
                                      double latitude,
                                      double distance,
                                      int availableSpotCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.address = address;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrls = imageUrls;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.availableSpotCount = availableSpotCount;
    }
}
