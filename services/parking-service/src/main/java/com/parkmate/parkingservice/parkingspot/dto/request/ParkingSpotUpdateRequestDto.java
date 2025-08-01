package com.parkmate.parkingservice.parkingspot.dto.request;

import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingSpotUpdateRequestDto {

    private Long parkingSpotId;
    private String parkingLotUuid;
    private String name;
    private ParkingSpotType type;
    private Set<EvChargeType> evChargeTypes;

    @Builder
    private ParkingSpotUpdateRequestDto(Long parkingSpotId,
                                        String parkingLotUuid,
                                        String name,
                                        ParkingSpotType type,
                                        Set<EvChargeType> evChargeTypes) {
        this.parkingSpotId = parkingSpotId;
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.type = type;
        this.evChargeTypes = evChargeTypes;
    }

    public static ParkingSpotUpdateRequestDto of(String parkingLotUuid,
                                                 Long parkingSpotId,
                                                 ParkingSpotUpdateRequestVo parkingSpotUpdateRequestVo) {

        return ParkingSpotUpdateRequestDto.builder()
                .parkingSpotId(parkingSpotId)
                .parkingLotUuid(parkingLotUuid)
                .name(parkingSpotUpdateRequestVo.getName())
                .type(parkingSpotUpdateRequestVo.getType())
                .evChargeTypes(parkingSpotUpdateRequestVo.getEvChargeTypes())
                .build();
    }

    public ParkingSpot toEntity() {
        return ParkingSpot.builder()
                .id(parkingSpotId)
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .type(type)
                .evChargeTypes(evChargeTypes)
                .build();
    }
}
