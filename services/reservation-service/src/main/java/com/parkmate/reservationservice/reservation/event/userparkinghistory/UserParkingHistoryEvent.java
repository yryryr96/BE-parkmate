package com.parkmate.reservationservice.reservation.event.userparkinghistory;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserParkingHistoryEvent {

    private String userUuid;
    private String parkingLotUuid;
    private String parkingSpotName;
    private String reservationCode;
    private String vehicleNumber;
    private HistoryType type;
    private LocalDateTime timestamp;
}
