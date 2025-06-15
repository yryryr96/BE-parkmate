package com.parkmate.parkingservice.parkinglot.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HostParkingLotResponseVoList {

    private List<HostParkingLotResponseVo> parkingLots;

    @Builder
    private HostParkingLotResponseVoList(List<HostParkingLotResponseVo> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
