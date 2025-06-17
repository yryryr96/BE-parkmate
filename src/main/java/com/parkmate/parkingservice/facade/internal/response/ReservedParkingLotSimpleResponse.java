package com.parkmate.parkingservice.facade.internal.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingLotSimpleResponse {

    private String thumbnailUrl;
    private String parkingLotUuid;
    private String parkingLotName;

    @Builder
    private ReservedParkingLotSimpleResponse(String thumbnailUrl,
                                             String parkingLotUuid,
                                             String parkingLotName) {
        this.thumbnailUrl = thumbnailUrl;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
    }

    public static ReservedParkingLotSimpleResponse from(ParkingLot parkingLot) {
        return ReservedParkingLotSimpleResponse.builder()
                .thumbnailUrl(parkingLot.getThumbnailUrl())
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .parkingLotName(parkingLot.getName())
                .build();
    }
}
