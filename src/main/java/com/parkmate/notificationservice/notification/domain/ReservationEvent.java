package com.parkmate.notificationservice.notification.domain;

import lombok.Getter;

@Getter
public class ReservationEvent {

    private String reservationUuid;
    private ReservationEventType eventType;
    private String userUuid;
    private String parkingLotUuid;
    private String vehicleNumber;
}
