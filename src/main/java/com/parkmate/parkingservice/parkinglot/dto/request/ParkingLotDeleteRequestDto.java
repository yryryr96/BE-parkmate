package com.parkmate.parkingservice.parkinglot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotDeleteRequestDto {

    private String parkingLotUuid;
    private String hostUuid;

    @Builder
    private ParkingLotDeleteRequestDto(String parkingLotUuid,
                                      String hostUuid) {
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
    }

    public static ParkingLotDeleteRequestDto of(String parkingLotUuid,
                                                String hostUuid) {
        return ParkingLotDeleteRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .hostUuid(hostUuid)
                .build();
    }
}
