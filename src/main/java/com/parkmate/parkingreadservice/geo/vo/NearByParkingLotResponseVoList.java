package com.parkmate.parkingreadservice.geo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NearByParkingLotResponseVoList {

    private List<NearbyParkingLotResponseVo> parkingLots;

    @Builder
    private NearByParkingLotResponseVoList(List<NearbyParkingLotResponseVo> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
