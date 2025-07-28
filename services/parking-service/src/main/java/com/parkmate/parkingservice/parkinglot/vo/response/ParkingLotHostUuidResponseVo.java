package com.parkmate.parkingservice.parkinglot.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotHostUuidResponseVo {

    private String hostUuid;

    @Builder
    private ParkingLotHostUuidResponseVo(String hostUuid) {
        this.hostUuid = hostUuid;
    }
}
