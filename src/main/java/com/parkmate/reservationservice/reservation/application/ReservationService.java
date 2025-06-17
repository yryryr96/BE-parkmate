package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.reservation.dto.request.*;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationsResponseDto;

public interface ReservationService {

    void reserve(ReservationCreateRequestDto reservationCreateRequestDto);

    void cancel(ReservationCancelRequestDto reservationCancelRequestDto);

    void modify(ReservationModifyRequestDto reservationModifyRequestDto);

    CursorPage<ReservationResponseDto> getReservations(ReservationCursorGetRequestDto reservationCursorGetRequestDto);

    ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto);
}
