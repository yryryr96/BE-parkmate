package com.parkmate.reservationservice.reservation.vo.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateRequestVo {

    private String parkingSpotType;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
}
