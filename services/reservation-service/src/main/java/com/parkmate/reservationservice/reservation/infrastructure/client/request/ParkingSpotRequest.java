package com.parkmate.reservationservice.reservation.infrastructure.client.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingSpotRequest {

    private String parkingLotUuid;
    private String parkingSpotType;
    private List<LocalDate> reservationDates;

    @Builder
    private ParkingSpotRequest(String parkingLotUuid,
                               String parkingSpotType,
                               List<LocalDate> reservationDates) {
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotType = parkingSpotType;
        this.reservationDates = reservationDates;
    }

    public static ParkingSpotRequest of(String parkingLotUuid,
                                        String parkingSpotType,
                                        List<LocalDate> reservationDates) {

        return ParkingSpotRequest.builder()
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotType(parkingSpotType)
                .reservationDates(reservationDates)
                .build();
    }
}
