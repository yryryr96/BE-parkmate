package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.common.exception.BaseException;
import com.parkmate.reservationservice.common.response.ResponseStatus;
import com.parkmate.reservationservice.reservation.event.ReservationEvent;
import com.parkmate.reservationservice.reservation.producer.ReservationNotificationProducer;
import com.parkmate.reservationservice.reservation.client.ParkingServiceClient;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationRequestDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.infrastructure.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationNotificationProducer reservationNotificationProducer;
    private final ParkingServiceClient parkingServiceClient;

    private static final int MODIFY_TIME_LIMIT_MINUTES = 60;

    @Transactional
    @Override
    public void reserve(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRepository.save(reservationRequestDto.toEntity());
        String hostUuid = parkingServiceClient.getHostUuidByParkingLotUuid(reservation.getParkingLotUuid()).getHostUuid();

        reservationNotificationProducer.send(ReservationEvent.from(hostUuid));
    }

    @Transactional
    @Override
    public void cancel(ReservationCancelRequestDto reservationCancelRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(reservationCancelRequestDto.getReservationCode(),
                        reservationCancelRequestDto.getUserUuid())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        reservation.cancel();
    }

    @Transactional
    @Override
    public void modify(ReservationModifyRequestDto reservationModifyRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                        reservationModifyRequestDto.getReservationCode(),
                        reservationModifyRequestDto.getUserUuid())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        if (canModified(reservation)) {
            reservation.modify(reservationModifyRequestDto.getNewEntryTime(),
                    reservationModifyRequestDto.getNewExitTime()
            );

            return;
        }

        throw new BaseException(ResponseStatus.MODIFY_TIME_LIMIT_EXCEEDED);
    }

    private boolean canModified(Reservation reservation) {

        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(reservation.getEntryTime().minusMinutes(MODIFY_TIME_LIMIT_MINUTES));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationResponseDto> getReservations(String userUuid) {

        return reservationRepository.findAllByUserUuid(userUuid)
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @Override
    public ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto) {
        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                        reservationGetRequestDto.getReservationCode(),
                        reservationGetRequestDto.getUserUuid())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ReservationResponseDto.from(reservation);
    }
}
