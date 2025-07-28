package com.parkmate.parkingservice.parkinglotoption.vo.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ParkingLotOptionResponseVoList {

    private List<ParkingLotOptionResponseVo> options;

    @Builder
    private ParkingLotOptionResponseVoList(List<ParkingLotOptionResponseVo> options) {
        this.options = options;
    }
}
