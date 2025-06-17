package com.parkmate.reservationservice.reservation.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCursorGetRequestVo {

    private Integer page;
    private Integer size;
    private Long cursor;
}
