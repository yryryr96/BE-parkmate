package com.parkmate.reservationservice.reservation.infrastructure.client.response;

import com.parkmate.reservationservice.reservation.vo.ParkingSpot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingSpotResponse {

    private String hostUuid;
    private String parkingLotUuid;
    private List<ParkingSpot> parkingSpots;
}
