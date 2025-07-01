package com.parkmate.reservationservice.reservation.dto.response;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.dto.request.PreReserveRequestDto;
import com.parkmate.reservationservice.reservation.vo.request.PreReserveRequestVo;
import com.parkmate.reservationservice.reservation.vo.response.PreReserveResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreReserveResponseDto {

    private String reservationCode;

    @Builder
    private PreReserveResponseDto(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public static PreReserveResponseDto from(Reservation reservation) {
        return PreReserveResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .build();
    }

    public PreReserveResponseVo toVo() {
        return PreReserveResponseVo.builder()
                .reservationCode(reservationCode)
                .build();
    }
}
