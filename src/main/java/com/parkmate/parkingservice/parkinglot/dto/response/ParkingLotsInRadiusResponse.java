package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.vo.response.NearbyParkingLotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ParkingLotsInRadiusResponse {

    private String parkingLotUuid;
    private double longitude;
    private double latitude;
    private double distance;

    @Builder
    private ParkingLotsInRadiusResponse(String parkingLotUuid,
                                        double longitude,
                                        double latitude,
                                        double distance) {
        this.parkingLotUuid = parkingLotUuid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public NearbyParkingLotResponseVo toVo() {
        return NearbyParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .longitude(longitude)
                .latitude(latitude)
                .distance(distance)
                .build();
    }
}
