package com.parkmate.parkingservice.parkingoperation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingOperationGetRequestDto {

    private String parkingLotUuid;
    private String parkingOperationUuid;

    @Builder
    private ParkingOperationGetRequestDto(String parkingLotUuid, String parkingOperationUuid) {
        this.parkingLotUuid = parkingLotUuid;
        this.parkingOperationUuid = parkingOperationUuid;
    }

    public static ParkingOperationGetRequestDto of(String parkingLotUuid, String parkingOperationUuid) {
        return ParkingOperationGetRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .parkingOperationUuid(parkingOperationUuid)
                .build();
    }
}
