package com.parkmate.parkingservice.parkingspot.vo.response;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotResponseVo {

    private String parkingLotUuid;
    private String name;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;
    private EvChargeType evChargeType;

    @Builder
    private ParkingSpotResponseVo(String parkingLotUuid,
                                  String name,
                                  ParkingSpotType type,
                                  Boolean isEvChargingAvailable,
                                  EvChargeType evChargeType) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeType = evChargeType;
    }
}
