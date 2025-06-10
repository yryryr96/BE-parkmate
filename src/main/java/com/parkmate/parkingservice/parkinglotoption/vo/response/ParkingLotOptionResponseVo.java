package com.parkmate.parkingservice.parkinglotoption.vo.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ParkingLotOptionResponseVo {

    private Long id;
    private String name;
    private String label;

    @Builder
    private ParkingLotOptionResponseVo(Long id,
                                      String name,
                                      String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }
}
