package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.common.exception.BaseException;
import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.common.response.ResponseStatus;
import com.parkmate.reservationservice.kafka.event.ReservationCreateEvent;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.dto.request.*;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.infrastructure.client.ParkingServiceClient;
import com.parkmate.reservationservice.reservation.infrastructure.client.request.ParkingSpotRequest;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ParkingLotAndSpotResponse;
import com.parkmate.reservationservice.reservation.infrastructure.repository.ReservationRepository;
import com.parkmate.reservationservice.reservation.vo.ParkingSpot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private static final int MODIFY_TIME_LIMIT_MINUTES = 60;

    private final ReservationRepository reservationRepository;
    private final ParkingServiceClient parkingServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void reserve(ReservationCreateRequestDto reservationCreateRequestDto) {

        ParkingLotAndSpotResponse parkingLot = fetchPotentialParkingSpots(reservationCreateRequestDto);

        Set<Long> unAvailableParkingSpotIds = getReservedParkingSpotIds(
                parkingLot.getParkingLotUuid(),
                reservationCreateRequestDto.getEntryTime(),
                reservationCreateRequestDto.getExitTime()
        );

        ParkingSpot availableSpot = parkingLot.getParkingSpots().stream()
                .filter(parkingSpot -> !unAvailableParkingSpotIds.contains(parkingSpot.getId()))
                .findFirst()
                .orElseThrow(() -> new BaseException(ResponseStatus.PARKING_LOT_NOT_AVAILABLE));

        Reservation reservation = reservationCreateRequestDto.toEntity(parkingLot.getParkingLotName(), availableSpot);

        reservationRepository.save(reservation);

        eventPublisher.publishEvent(ReservationCreateEvent.from(parkingLot.getHostUuid(), reservation));
    }

    @Transactional
    @Override
    public void cancel(ReservationCancelRequestDto reservationCancelRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                reservationCancelRequestDto.getReservationCode(),
                reservationCancelRequestDto.getUserUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        reservation.cancel();
    }

    @Transactional
    @Override
    public void modify(ReservationModifyRequestDto reservationModifyRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                reservationModifyRequestDto.getReservationCode(),
                reservationModifyRequestDto.getUserUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        if (canModified(reservation)) {
            reservation.modify(reservationModifyRequestDto.getNewEntryTime(),
                    reservationModifyRequestDto.getNewExitTime()
            );

            return;
        }

        throw new BaseException(ResponseStatus.MODIFY_TIME_LIMIT_EXCEEDED);
    }

    @Transactional(readOnly = true)
    @Override
    public CursorPage<ReservationResponseDto> getReservations(
            ReservationCursorGetRequestDto reservationCursorGetRequestDto) {

        return reservationRepository.getReservations(reservationCursorGetRequestDto)
                .map(ReservationResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                        reservationGetRequestDto.getReservationCode(),
                        reservationGetRequestDto.getUserUuid())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ReservationResponseDto.from(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Long> getReservedParkingSpotIds(String parkingLotUuid,
                                               LocalDateTime entryTime,
                                               LocalDateTime exitTime) {

        List<Reservation> existingReservations = reservationRepository.findAllByParkingLotUuid(
                parkingLotUuid,
                entryTime,
                exitTime
        );
        return existingReservations.stream()
                .map(Reservation::getParkingSpotId)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationResponseDto getReservationForUse(ReservationGetForUseDto reservationGetForUseDto) {

        Reservation reservation = reservationRepository.findByUserUuidAndReservationCodeAndParkingLotUuid(
                reservationGetForUseDto.getUserUuid(),
                reservationGetForUseDto.getReservationCode(),
                reservationGetForUseDto.getParkingLotUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ReservationResponseDto.from(reservation);
    }

    @Transactional
    @Override
    public void changeStatus(String reservationCode,
                             ReservationStatus reservationStatus) {

        Reservation reservation = reservationRepository.findByReservationCode(reservationCode)
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        reservation.changeStatus(reservationStatus);
    }

    private ParkingLotAndSpotResponse fetchPotentialParkingSpots(
            ReservationCreateRequestDto reservationCreateRequestDto) {

        List<LocalDate> requestDates = reservationCreateRequestDto.getEntryTime().toLocalDate()
                .datesUntil(reservationCreateRequestDto.getExitTime().toLocalDate().plusDays(1))
                .toList();

        ParkingSpotRequest parkingSpotRequest = ParkingSpotRequest.of(
                reservationCreateRequestDto.getParkingLotUuid(),
                reservationCreateRequestDto.getParkingSpotType(),
                requestDates
        );

        ParkingLotAndSpotResponse clientResponse = parkingServiceClient.getParkingSpots(parkingSpotRequest)
                .orElseThrow(() -> new BaseException(ResponseStatus.PARKING_LOT_NOT_AVAILABLE));

        if (clientResponse.getParkingSpots() == null || clientResponse.getParkingSpots().isEmpty()) {
            throw new BaseException(ResponseStatus.PARKING_LOT_NOT_AVAILABLE);
        }

        return clientResponse;
    }

    private boolean canModified(Reservation reservation) {

        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(reservation.getEntryTime().minusMinutes(MODIFY_TIME_LIMIT_MINUTES));
    }
}
