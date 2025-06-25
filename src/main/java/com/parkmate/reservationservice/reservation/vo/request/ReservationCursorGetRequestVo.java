package com.parkmate.reservationservice.reservation.vo.request;

import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCursorGetRequestVo {

    private ReservationStatus status;
    private Integer page;
    private Integer size;
    private Long cursor;
}
