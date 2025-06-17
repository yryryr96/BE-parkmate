package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCreateRequestDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationsResponseDto;

import java.util.List;

public interface ReservationService {

    void reserve(ReservationCreateRequestDto reservationCreateRequestDto);

    void cancel(ReservationCancelRequestDto reservationCancelRequestDto);

    void modify(ReservationModifyRequestDto reservationModifyRequestDto);

    ReservationsResponseDto getReservations(String userUuid);

    ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto);
}
