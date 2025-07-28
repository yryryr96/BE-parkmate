package com.parkmate.reservationservice.reservation.infrastructure.repository;

import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCursorGetRequestDto;

public interface ReservationRepositoryCustom {

    CursorPage<Reservation> getReservations(ReservationCursorGetRequestDto reservationCursorGetRequestDto);
}
