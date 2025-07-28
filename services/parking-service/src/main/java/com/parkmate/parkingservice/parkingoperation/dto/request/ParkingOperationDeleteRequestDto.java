package com.parkmate.parkingservice.parkingoperation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingOperationDeleteRequestDto {

    private String parkingLotUuid;
    private String parkingOperationUuid;

    @Builder
    private ParkingOperationDeleteRequestDto(String parkingLotUuid,
                                            String parkingOperationUuid) {
        this.parkingLotUuid = parkingLotUuid;
        this.parkingOperationUuid = parkingOperationUuid;
    }

    public static ParkingOperationDeleteRequestDto of(String parkingLotUuid,
                                                      String parkingOperationUuid) {
        return ParkingOperationDeleteRequestDto.builder()
                .parkingOperationUuid(parkingOperationUuid)
                .parkingLotUuid(parkingLotUuid)
                .build();
    }
}
