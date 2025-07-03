package com.parkmate.reservationservice.reservation.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreReserveResponseVo {

    private String reservationCode;
    private long amount;

    @Builder
    private PreReserveResponseVo(String reservationCode, long amount) {
        this.reservationCode = reservationCode;
        this.amount = amount;
    }
}
