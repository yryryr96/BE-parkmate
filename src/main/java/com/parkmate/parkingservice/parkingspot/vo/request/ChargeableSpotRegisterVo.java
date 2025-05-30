package com.parkmate.parkingservice.parkingspot.vo.request;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Getter;

import java.util.List;

@Getter
public class ChargeableSpotRegisterVo {

    private ParkingSpotType parkingSpotType;
    private List<EvChargeType> evChargeTypes;
}
