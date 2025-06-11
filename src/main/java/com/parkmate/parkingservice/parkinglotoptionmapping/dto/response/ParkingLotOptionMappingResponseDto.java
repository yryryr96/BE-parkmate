package com.parkmate.parkingservice.parkinglotoptionmapping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotOptionMappingResponseDto {

    private String parkingLotUuid;
    private List<Long> optionIds;

    @Builder
    private ParkingLotOptionMappingResponseDto(String parkingLotUuid,
                                               List<Long> optionIds) {
        this.parkingLotUuid = parkingLotUuid;
        this.optionIds = optionIds;
    }

    public static ParkingLotOptionMappingResponseDto of(String parkingLotUuid,
                                                          List<Long> optionIds) {

        return ParkingLotOptionMappingResponseDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .optionIds(optionIds)
                .build();
    }
}
