package com.parkmate.parkingreadservice.geo.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserParkingLotDistanceResponseVo {

    private double distance;

    @Builder
    private UserParkingLotDistanceResponseVo(double distance) {
        this.distance = distance;
    }

    public static UserParkingLotDistanceResponseVo of(double distance) {
        return UserParkingLotDistanceResponseVo.builder()
                .distance(distance)
                .build();
    }


}
