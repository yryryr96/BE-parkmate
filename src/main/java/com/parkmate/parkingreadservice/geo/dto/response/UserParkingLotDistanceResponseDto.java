package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.UserParkingLotDistanceResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserParkingLotDistanceResponseDto {

    private double distance;

    @Builder
    private UserParkingLotDistanceResponseDto(double distance) {
        this.distance = distance;
    }

    public static UserParkingLotDistanceResponseDto of(double distance) {
        return UserParkingLotDistanceResponseDto.builder()
                .distance(distance)
                .build();
    }

    public UserParkingLotDistanceResponseVo toVo() {
        return UserParkingLotDistanceResponseVo.builder()
                .distance(distance)
                .build();
    }
}
