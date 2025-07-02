package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.dto.request.*;
import com.parkmate.reservationservice.reservation.dto.response.PreReserveResponseDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ReservationService {

    PreReserveResponseDto preReserve(PreReserveRequestDto preReserveRequestDto);

    void reserve(ReservationCreateRequestDto reservationCreateRequestDto);

    void cancel(ReservationCancelRequestDto reservationCancelRequestDto);

    void modify(ReservationModifyRequestDto reservationModifyRequestDto);

    CursorPage<ReservationResponseDto> getReservations(ReservationCursorGetRequestDto reservationCursorGetRequestDto);

    ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto);

    Set<Long> getReservedParkingSpotIds(String parkingLotUuid,
                                        LocalDateTime entryTime,
                                        LocalDateTime exitTime);

    ReservationResponseDto getReservationForUse(ReservationGetForUseDto reservationGetForUseDto);

    void changeStatus(String reservationCode, ReservationStatus reservationStatus);
}
