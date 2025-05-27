package com.parkmate.parking_service.parkinglot.vo.request;

import com.parkmate.parking_service.parkinglot.domain.ParkingLotType;
import lombok.Getter;

@Getter
public class ParkingLotCreateRequestVo {

    private String hostUuid;
    private ParkingLotType parkingLotType;
    private String name;
    private String phoneNumber;
    private int capacity;
    private int registeredCapacity;
    private String zoneCode;
    private String mainAddress;
    private String detailAddress;
    private double latitude;
    private double longitude;
    private Boolean isEvChargingAvailable;
    private String extraInfo;
}
