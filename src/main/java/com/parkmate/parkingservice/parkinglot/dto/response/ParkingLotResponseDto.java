package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotType;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotResponseDto {

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
    private ParkingLotResponseDto(String hostUuid,
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

    public static ParkingLotResponseDto from(ParkingLot parkingLot) {
        return ParkingLotResponseDto.builder()
                .hostUuid(parkingLot.getHostUuid())
                .parkingLotType(parkingLot.getParkingLotType())
                .name(parkingLot.getName())
                .phoneNumber(parkingLot.getPhoneNumber())
                .capacity(parkingLot.getCapacity())
                .registeredCapacity(parkingLot.getRegisteredCapacity())
                .zoneCode(parkingLot.getZoneCode())
                .mainAddress(parkingLot.getMainAddress())
                .detailAddress(parkingLot.getDetailAddress())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .isEvChargingAvailable(parkingLot.getIsEvChargingAvailable())
                .extraInfo(parkingLot.getExtraInfo())
                .build();
    }

    public ParkingLotResponseVo toVo() {

        return ParkingLotResponseVo.builder()
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
