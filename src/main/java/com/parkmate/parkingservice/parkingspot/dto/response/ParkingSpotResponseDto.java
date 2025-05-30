package com.parkmate.parkingservice.parkingspot.dto.response;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.vo.response.ParkingSpotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingSpotResponseDto {

    private String parkingLotUuid;
    private String name;
    private ParkingSpotType type;
    private Set<EvChargeType> evChargeTypes;

    @Builder
    private ParkingSpotResponseDto(String parkingLotUuid,
                                   String name,
                                   ParkingSpotType type,
                                   Set<EvChargeType> evChargeTypes) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.type = type;
        this.evChargeTypes = evChargeTypes;
    }

    public static ParkingSpotResponseDto from(ParkingSpot parkingSpot) {
        return ParkingSpotResponseDto.builder()
                .parkingLotUuid(parkingSpot.getParkingLotUuid())
                .name(parkingSpot.getName())
                .type(parkingSpot.getType())
                .evChargeTypes(parkingSpot.getEvChargeTypes())
                .build();
    }

    public ParkingSpotResponseVo toVo() {
        return ParkingSpotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .type(type)
                .evChargeTypes(evChargeTypes)
                .build();
    }
}
