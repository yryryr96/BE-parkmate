package com.parkmate.parkingservice.parkingspot.dto.request;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotRegisterRequestDto {

    private String name;
    private String parkingLotUuid;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;

    @Builder
    private ParkingSpotRegisterRequestDto(String name,
                                          String parkingLotUuid,
                                          ParkingSpotType type,
                                          Boolean isEvChargingAvailable) {
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
    }
}
