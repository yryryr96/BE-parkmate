package com.parkmate.reservationservice.reservation.vo.request;

import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationCursorGetRequestVo {

    private List<ReservationStatus> status;
    private Integer page;
    private Integer size;
    private Long cursor;
}
