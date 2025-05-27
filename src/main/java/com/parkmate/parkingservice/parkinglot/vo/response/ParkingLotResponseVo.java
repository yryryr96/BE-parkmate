package com.parkmate.parkingservice.parkinglot.vo.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLotType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotResponseVo {

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

    @Builder
    private ParkingLotResponseVo(String hostUuid,
                                ParkingLotType parkingLotType,
                                String name,
                                String phoneNumber,
                                int capacity,
                                int registeredCapacity,
                                String zoneCode,
                                String mainAddress,
                                String detailAddress,
                                double latitude,
                                double longitude,
                                Boolean isEvChargingAvailable,
                                String extraInfo) {
        this.hostUuid = hostUuid;
        this.parkingLotType = parkingLotType;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.registeredCapacity = registeredCapacity;
        this.zoneCode = zoneCode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.extraInfo = extraInfo;
    }
}
