package com.parkmate.parking_service.parkinglot.dto.request;

import com.parkmate.parking_service.common.generator.UUIDGenerator;
import com.parkmate.parking_service.parkinglot.domain.ParkingLot;
import com.parkmate.parking_service.parkinglot.domain.ParkingLotType;
import com.parkmate.parking_service.parkinglot.vo.request.ParkingLotCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotCreateRequestDto {

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
    private ParkingLotCreateRequestDto(String hostUuid,
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

    public static ParkingLotCreateRequestDto from(ParkingLotCreateRequestVo parkingLotCreateRequestVo) {
        return ParkingLotCreateRequestDto.builder()
                .hostUuid(parkingLotCreateRequestVo.getHostUuid())
                .parkingLotType(parkingLotCreateRequestVo.getParkingLotType())
                .name(parkingLotCreateRequestVo.getName())
                .phoneNumber(parkingLotCreateRequestVo.getPhoneNumber())
                .capacity(parkingLotCreateRequestVo.getCapacity())
                .registeredCapacity(parkingLotCreateRequestVo.getRegisteredCapacity())
                .zoneCode(parkingLotCreateRequestVo.getZoneCode())
                .mainAddress(parkingLotCreateRequestVo.getMainAddress())
                .detailAddress(parkingLotCreateRequestVo.getDetailAddress())
                .latitude(parkingLotCreateRequestVo.getLatitude())
                .longitude(parkingLotCreateRequestVo.getLongitude())
                .isEvChargingAvailable(parkingLotCreateRequestVo.getIsEvChargingAvailable())
                .extraInfo(parkingLotCreateRequestVo.getExtraInfo())
                .build();
    }

    public ParkingLot toEntity() {
        return ParkingLot.builder()
                .parkingLotUuid(UUIDGenerator.generateUUID())
                .hostUuid(this.hostUuid)
                .parkingLotType(this.parkingLotType)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .capacity(this.capacity)
                .registeredCapacity(this.registeredCapacity)
                .zoneCode(this.zoneCode)
                .mainAddress(this.mainAddress)
                .detailAddress(this.detailAddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .isEvChargingAvailable(this.isEvChargingAvailable)
                .extraInfo(this.extraInfo)
                .build();
    }
}
