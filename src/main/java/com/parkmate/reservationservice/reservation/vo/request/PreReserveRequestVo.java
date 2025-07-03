package com.parkmate.reservationservice.reservation.vo.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class PreReserveRequestVo {

    private String parkingSpotType;
    private String parkingLotUuid;
    private String vehicleNumber;
    private long amount;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
