package com.parkmate.reservationservice.reservation.client.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotClientResponse {

    private String hostUuid;

    public ParkingLotClientResponse(String hostUuid) {
        this.hostUuid = hostUuid;
    }
}
