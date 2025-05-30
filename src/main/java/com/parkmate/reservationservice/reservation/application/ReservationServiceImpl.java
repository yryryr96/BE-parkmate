package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationRequestDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.infrastructure.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public void reserve(ReservationRequestDto reservationRequestDto) {
        reservationRepository.save(reservationRequestDto.toEntity());
    }

    @Override
    public void cancel(ReservationCancelRequestDto reservationCancelRequestDto) {

    }

    @Override
    public void modify(ReservationModifyRequestDto reservationModifyRequestDto) {

    }

    @Override
    public List<ReservationResponseDto> getReservations(String userUuid) {
        return null;
    }

    @Override
    public ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto) {
        return null;
    }
}
