package com.parkmate.parkingservice.parkingoperation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
public class ParkingOperationDeleteRequestDto {

    private final String parkingOperationUuid;
    private final String parkingLotUuid;

    @Builder
    private ParkingOperationDeleteRequestDto(String parkingOperationUuid,
                                             String parkingLotUuid) {
        this.parkingOperationUuid = parkingOperationUuid;
        this.parkingLotUuid = parkingLotUuid;
    }

    public static ParkingOperationDeleteRequestDto of(String parkingLotUuid,
                                                      String parkingOperationUuid) {
        return ParkingOperationDeleteRequestDto.builder()
                .parkingOperationUuid(parkingOperationUuid)
                .parkingLotUuid(parkingLotUuid)
                .build();
    }
}
