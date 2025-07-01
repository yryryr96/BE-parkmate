package com.parkmate.reservationservice.reservation.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreReserveResponseVo {

    private String reservationCode;

    @Builder
    private PreReserveResponseVo(String reservationCode) {
        this.reservationCode = reservationCode;
    }
}
