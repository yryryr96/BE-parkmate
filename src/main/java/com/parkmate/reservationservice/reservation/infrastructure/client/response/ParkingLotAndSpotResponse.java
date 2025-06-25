package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import com.parkmate.reservationservice.reservation.vo.ParkingSpot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotAndSpotResponse {

    private String hostUuid;
    private String parkingLotUuid;
    private String parkingLotName;
    private List<ParkingSpot> parkingSpots;
}
