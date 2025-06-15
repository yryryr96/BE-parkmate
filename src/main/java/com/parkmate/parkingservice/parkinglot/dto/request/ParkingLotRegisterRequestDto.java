package com.parkmate.parkingservice.parkinglot.dto.request;

import com.parkmate.parkingservice.common.generator.UUIDGenerator;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotType;
import com.parkmate.parkingservice.parkinglot.vo.request.ParkingLotRegisterRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotRegisterRequestDto {

    private String hostUuid;
    private ParkingLotType parkingLotType;
    private String name;
    private String phoneNumber;
    private int capacity;
    private int registeredCapacity;
    private String mainAddress;
    private String detailAddress;
    private double latitude;
    private double longitude;
    private Boolean isEvChargingAvailable;
    private String extraInfo;
    private String thumbnailUrl;

    @Builder
    private ParkingLotRegisterRequestDto(String hostUuid,
                                         ParkingLotType parkingLotType,
                                         String name,
                                         String phoneNumber,
                                         int capacity,
                                         int registeredCapacity,
                                         String mainAddress,
                                         String detailAddress,
                                         double latitude,
                                         double longitude,
                                         Boolean isEvChargingAvailable,
                                         String extraInfo,
                                         String thumbnailUrl) {
        this.hostUuid = hostUuid;
        this.parkingLotType = parkingLotType;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.registeredCapacity = registeredCapacity;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.extraInfo = extraInfo;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static ParkingLotRegisterRequestDto from(ParkingLotRegisterRequestVo parkingLotRegisterRequestVo) {
        return ParkingLotRegisterRequestDto.builder()
                .hostUuid(parkingLotRegisterRequestVo.getHostUuid())
                .parkingLotType(parkingLotRegisterRequestVo.getParkingLotType())
                .name(parkingLotRegisterRequestVo.getName())
                .phoneNumber(parkingLotRegisterRequestVo.getPhoneNumber())
                .capacity(parkingLotRegisterRequestVo.getCapacity())
                .registeredCapacity(parkingLotRegisterRequestVo.getRegisteredCapacity())
                .mainAddress(parkingLotRegisterRequestVo.getMainAddress())
                .detailAddress(parkingLotRegisterRequestVo.getDetailAddress())
                .latitude(parkingLotRegisterRequestVo.getLatitude())
                .longitude(parkingLotRegisterRequestVo.getLongitude())
                .isEvChargingAvailable(parkingLotRegisterRequestVo.getIsEvChargingAvailable())
                .extraInfo(parkingLotRegisterRequestVo.getExtraInfo())
                .thumbnailUrl(parkingLotRegisterRequestVo.getThumbnailUrl())
                .build();
    }

    public ParkingLot toEntity() {
        return ParkingLot.builder()
                .parkingLotUuid(UUIDGenerator.generateUUID())
                .hostUuid(hostUuid)
                .parkingLotType(parkingLotType)
                .name(name)
                .phoneNumber(phoneNumber)
                .capacity(capacity)
                .registeredCapacity(registeredCapacity)
                .mainAddress(mainAddress)
                .detailAddress(detailAddress)
                .latitude(latitude)
                .longitude(longitude)
                .isEvChargingAvailable(isEvChargingAvailable)
                .extraInfo(extraInfo)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
