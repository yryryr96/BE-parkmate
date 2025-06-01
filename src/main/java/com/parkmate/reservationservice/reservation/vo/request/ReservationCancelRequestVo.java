package com.parkmate.reservationservice.reservation.vo.request;

import lombok.Getter;

@Getter
public class ReservationCancelRequestVo {

    private String userUuid;
    private String cancelReason;
}
