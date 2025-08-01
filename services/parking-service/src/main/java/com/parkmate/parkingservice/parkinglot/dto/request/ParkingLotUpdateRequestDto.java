package com.parkmate.parkingservice.parkinglot.dto.request;

import com.parkmate.parkingservice.parkinglot.vo.request.ParkingLotUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotUpdateRequestDto {

    private String parkingLotUuid;
    private String hostUuid;
    private String name;
    private String phoneNumber;
    private int capacity;
    private int registeredCapacity;
    private Boolean isEvChargingAvailable;
    private String extraInfo;

    @Builder
    private ParkingLotUpdateRequestDto(String parkingLotUuid,
                                      String hostUuid,
                                      String name,
                                      String phoneNumber,
                                      int capacity,
                                      int registeredCapacity,
                                      Boolean isEvChargingAvailable,
                                      String extraInfo) {
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.registeredCapacity = registeredCapacity;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.extraInfo = extraInfo;
    }

    public static ParkingLotUpdateRequestDto from(String parkingLotUuid,
                                                  String hostUuid,
                                                  ParkingLotUpdateRequestVo parkingLotUpdateRequestVo) {
        return ParkingLotUpdateRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .hostUuid(hostUuid)
                .name(parkingLotUpdateRequestVo.getName())
                .phoneNumber(parkingLotUpdateRequestVo.getPhoneNumber())
                .capacity(parkingLotUpdateRequestVo.getCapacity())
                .registeredCapacity(parkingLotUpdateRequestVo.getRegisteredCapacity())
                .isEvChargingAvailable(parkingLotUpdateRequestVo.getIsEvChargingAvailable())
                .extraInfo(parkingLotUpdateRequestVo.getExtraInfo())
                .build();
    }
}
