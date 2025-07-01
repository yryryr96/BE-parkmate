package com.parkmate.reservationservice.reservation.vo.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PreReserveRequestVo {

    private String parkingSpotType;
    private String parkingLotUuid;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
