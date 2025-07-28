package com.parkmate.parkingreadservice.geo.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NearbyParkingLotResponseVoList {

    private List<NearbyParkingLotResponseVo> parkingLots;

    @Builder
    private NearbyParkingLotResponseVoList(List<NearbyParkingLotResponseVo> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
