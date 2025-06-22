package com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response;

import lombok.Getter;

@Getter
public class ParkingLotAndSpotResponse {

    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;
}
