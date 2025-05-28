package com.parkmate.parkingservice.parkingspot.vo.request;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Getter;

@Getter
public class ParkingSpotRegisterRequestVo {

    private String name;
    private String parkingLotUuid;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;
}
