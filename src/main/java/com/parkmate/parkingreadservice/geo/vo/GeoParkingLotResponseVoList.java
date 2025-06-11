package com.parkmate.parkingreadservice.geo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GeoParkingLotResponseVoList {

    private List<GeoParkingLotResponseVo> parkingLots;

    @Builder
    private GeoParkingLotResponseVoList(List<GeoParkingLotResponseVo> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
