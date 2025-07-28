package com.parkmate.reservationservice.reservation.dto.response;

import com.parkmate.reservationservice.reservation.vo.response.ReservationsResponseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationsResponseDto {

    private List<ReservationResponseDto> reservations;

    private ReservationsResponseDto(List<ReservationResponseDto> reservations) {
        this.reservations = reservations;
    }

    public static ReservationsResponseDto from(List<ReservationResponseDto> reservations) {
        return new ReservationsResponseDto(reservations);
    }

    public ReservationsResponseVo toVo() {
        return ReservationsResponseVo.from(reservations.stream()
                .map(ReservationResponseDto::toVo)
                .toList());

    }
}
