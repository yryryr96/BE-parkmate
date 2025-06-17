package com.parkmate.reservationservice.reservation.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationsResponseVo {

    private List<ReservationResponseVo> reservations;

    private ReservationsResponseVo(List<ReservationResponseVo> reservations) {
        this.reservations = reservations;
    }

    public static ReservationsResponseVo from(List<ReservationResponseVo> reservations) {
        return new ReservationsResponseVo(reservations);
    }
}
