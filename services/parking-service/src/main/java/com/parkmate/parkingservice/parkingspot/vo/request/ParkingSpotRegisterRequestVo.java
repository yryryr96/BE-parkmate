package com.parkmate.parkingservice.parkingspot.vo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ParkingSpotRegisterRequestVo {

    private List<ChargeableSpotRegisterVo> chargeable;
    private List<NonChargeableSpotRegisterVo> nonChargeable;
}
