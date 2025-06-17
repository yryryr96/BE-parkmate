package com.parkmate.parkingservice.facade.internal.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservedParkingLotResponse {

    private String thumbnailUrl;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;

    @Builder
    private ReservedParkingLotResponse(String thumbnailUrl,
                                       String parkingLotUuid,
                                       String parkingLotName,
                                       Long parkingSpotId,
                                       String parkingSpotName) {
        this.thumbnailUrl = thumbnailUrl;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
    }

    public static ReservedParkingLotResponse of(ParkingLot parkingLot,
                                                ParkingSpot parkingSpot) {
        return ReservedParkingLotResponse.builder()
                .thumbnailUrl(parkingLot.getThumbnailUrl())
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .parkingLotName(parkingLot.getName())
                .parkingSpotId(parkingSpot.getId())
                .parkingSpotName(parkingSpot.getName())
                .build();
    }
}
