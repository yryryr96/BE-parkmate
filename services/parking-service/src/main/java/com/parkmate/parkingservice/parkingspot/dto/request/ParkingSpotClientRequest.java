package com.parkmate.parkingservice.parkingspot.dto.request;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingSpotClientRequest {

    private String parkingLotUuid;
    private ParkingSpotType parkingSpotType;
}
