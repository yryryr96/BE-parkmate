package com.parkmate.parkingreadservice.parkinglotread.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReadSimpleResponseVo {

    private String parkingLotUuid;
    private String thumbnailUrl;
    private String name;
    private String address;
    private double rating;
    private int reviewCount;

    @Builder
    private ParkingLotReadSimpleResponseVo(String parkingLotUuid,
                                           String thumbnailUrl,
                                           String name,
                                           String address,
                                           double rating,
                                           int reviewCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}
