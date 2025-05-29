package com.parkmate.parkingservice.parkingspot.vo.request;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Getter;

@Getter
public class NonChargeableSpotRegisterVo {

    private ParkingSpotType parkingSpotType;
    private int count;
}
