package com.parkmate.reservationservice.reservation.vo.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationModifyRequestVo {

    private LocalDateTime newEntryTime;
    private LocalDateTime newExitTime;
}
