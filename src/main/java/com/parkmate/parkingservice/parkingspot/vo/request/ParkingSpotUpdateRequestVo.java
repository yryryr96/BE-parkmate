package com.parkmate.parkingservice.parkingspot.vo.request;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Getter;

import java.util.Set;

@Getter
public class ParkingSpotUpdateRequestVo {

    private String name;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;
    private Set<EvChargeType> evChargeTypes;
}
