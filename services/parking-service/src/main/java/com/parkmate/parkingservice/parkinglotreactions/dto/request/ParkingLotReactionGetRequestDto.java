package com.parkmate.parkingservice.parkinglotreactions.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReactionGetRequestDto {

    private String parkingLotUuid;
    private String userUuid;

    @Builder
    private ParkingLotReactionGetRequestDto(String parkingLotUuid,
                                            String userUuid) {
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
    }

    public static ParkingLotReactionGetRequestDto of(String parkingLotUuid, String userUuid) {
        return ParkingLotReactionGetRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .userUuid(userUuid)
                .build();
    }
}
