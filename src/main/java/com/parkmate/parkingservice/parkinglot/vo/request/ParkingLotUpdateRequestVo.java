package com.parkmate.parkingservice.parkinglot.vo.request;

import lombok.Getter;

@Getter
public class ParkingLotUpdateRequestVo {

    private String hostUuid;
    private String name;
    private String phoneNumber;
    private int capacity;
    private int registeredCapacity;
    private Boolean isEvChargingAvailable;
    private String extraInfo;
}
