package com.parkmate.parkingservice.parkinglotoptionmapping.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotMappingUpdateRequestDto {

    private String parkingLotUuid;
    private List<Long> optionIds;

    @Builder
    private ParkingLotMappingUpdateRequestDto(String parkingLotUuid,
                                              List<Long> optionIds) {
        this.parkingLotUuid = parkingLotUuid;
        this.optionIds = optionIds;
    }

    public static ParkingLotMappingUpdateRequestDto of(String parkingLotUuid,
                                                       List<Long> optionIds) {
        return ParkingLotMappingUpdateRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .optionIds(optionIds)
                .build();
    }
}
