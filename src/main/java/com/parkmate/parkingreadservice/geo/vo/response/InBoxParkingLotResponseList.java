package com.parkmate.parkingreadservice.geo.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseList {

    private List<InBoxParkingLotResponseVo> parkingLots;

    @Builder
    private InBoxParkingLotResponseList(List<InBoxParkingLotResponseVo> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
