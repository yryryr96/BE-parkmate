package com.parkmate.parkingservice.parkingspot.vo.response;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingSpotResponseVo {

    private String parkingLotUuid;
    private String name;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;
    private Set<EvChargeType> evChargeTypes;

    @Builder
    private ParkingSpotResponseVo(String parkingLotUuid,
                                  String name,
                                  ParkingSpotType type,
                                  Boolean isEvChargingAvailable,
                                  Set<EvChargeType> evChargeTypes) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
    }
}
