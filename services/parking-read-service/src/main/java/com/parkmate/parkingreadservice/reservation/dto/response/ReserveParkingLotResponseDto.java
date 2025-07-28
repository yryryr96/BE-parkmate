package com.parkmate.parkingreadservice.reservation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReserveParkingLotResponseDto {

    private String parkingLotUuid;
    private int reservedSpotCount;
}
