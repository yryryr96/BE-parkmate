package com.parkmate.parkingservice.parkingspot.dto.response;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.vo.response.ParkingSpotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class ParkingSpotResponseDto {

    private String parkingLotUuid;
    private String name;
    private ParkingSpotType type;
    private Boolean isEvChargingAvailable;
    private EvChargeType evChargeType;

    @Builder
    private ParkingSpotResponseDto(String parkingLotUuid,
                                   String name,
                                   ParkingSpotType type,
                                   Boolean isEvChargingAvailable,
                                   EvChargeType evChargeType) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeType = evChargeType;
    }

    public static ParkingSpotResponseDto from(ParkingSpot parkingSpot) {
        return ParkingSpotResponseDto.builder()
                .parkingLotUuid(parkingSpot.getParkingLotUuid())
                .name(parkingSpot.getName())
                .type(parkingSpot.getType())
                .isEvChargingAvailable(parkingSpot.getIsEvChargingAvailable())
                .evChargeType(parkingSpot.getEvChargeType())
                .build();
    }

    public ParkingSpotResponseVo toVo() {
        return ParkingSpotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .type(type)
                .isEvChargingAvailable(isEvChargingAvailable)
                .evChargeType(evChargeType)
                .build();
    }
}
