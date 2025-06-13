package com.parkmate.reservationservice.reservation.application;

import com.parkmate.reservationservice.common.exception.BaseException;
import com.parkmate.reservationservice.common.generator.ReservationCodeGenerator;
import com.parkmate.reservationservice.common.response.ResponseStatus;
import com.parkmate.reservationservice.kafka.event.ReservationEvent;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCreateRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.infrastructure.client.ParkingServiceClient;
import com.parkmate.reservationservice.reservation.infrastructure.client.request.ParkingSpotRequest;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ParkingSpotResponse;
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

    private final ReservationRepository reservationRepository;
    private final ParkingServiceClient parkingServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    private static final int MODIFY_TIME_LIMIT_MINUTES = 60;

    @Transactional
    @Override
    public void reserve(ReservationCreateRequestDto reservationCreateRequestDto) {

        ParkingSpotResponse clientResponse = fetchPotentialParkingSpots(reservationCreateRequestDto);

        Set<Long> unAvailableParkingSpotIds = getUnavailableParkingSpotIds(
                clientResponse.getParkingLotUuid(),
                reservationCreateRequestDto.getEntryTime(),
                reservationCreateRequestDto.getExitTime()
        );

        ParkingSpot availableSpot = findFirstAvailableSpot(clientResponse.getParkingSpots(),
                unAvailableParkingSpotIds);

        Reservation reservation = Reservation.builder()
                .reservationCode(ReservationCodeGenerator.generate())
                .userUuid(reservationCreateRequestDto.getUserUuid())
                .parkingLotUuid(reservationCreateRequestDto.getParkingLotUuid())
                .parkingSpotId(availableSpot.getId())
                .vehicleNumber(reservationCreateRequestDto.getVehicleNumber())
                .entryTime(reservationCreateRequestDto.getEntryTime())
                .exitTime(reservationCreateRequestDto.getExitTime())
                .amount(reservationCreateRequestDto.getAmount())
                .paymentType(reservationCreateRequestDto.getPaymentType())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        eventPublisher.publishEvent(ReservationEvent.from(clientResponse.getHostUuid(), reservation));
    }

    private ParkingSpotResponse fetchPotentialParkingSpots(ReservationCreateRequestDto reservationCreateRequestDto) {

        List<LocalDate> requestDates = reservationCreateRequestDto.getEntryTime().toLocalDate()
                .datesUntil(reservationCreateRequestDto.getExitTime().toLocalDate().plusDays(1))
                .toList();

        ParkingSpotRequest parkingSpotRequest = ParkingSpotRequest.of(
                reservationCreateRequestDto.getParkingLotUuid(),
                reservationCreateRequestDto.getParkingSpotType(),
                requestDates
        );

        ParkingSpotResponse clientResponse = parkingServiceClient.getParkingSpots(parkingSpotRequest);

        if (clientResponse.getParkingSpots().isEmpty()) {
            throw new BaseException(ResponseStatus.PARKING_LOT_NOT_AVAILABLE);
        }
        return clientResponse;
    }

    private Set<Long> getUnavailableParkingSpotIds(String parkingLotUuid,
                                                   LocalDateTime entryTime,
                                                   LocalDateTime exitTime) {
        List<Reservation> existingReservations = reservationRepository.findAllByParkingLotUuidAndStatus(
                parkingLotUuid,
                entryTime,
                exitTime,
                ReservationStatus.CONFIRMED
        );

        return existingReservations.stream()
                .map(Reservation::getParkingSpotId)
                .collect(Collectors.toSet());
    }

    private ParkingSpot findFirstAvailableSpot(List<ParkingSpot> parkingSpots,
                                                      Set<Long> unAvailableParkingSpotIds) {

        return parkingSpots.stream()
                .filter(parkingSpot -> !unAvailableParkingSpotIds.contains(parkingSpot.getId()))
                .findFirst()
                .orElseThrow(() -> new BaseException(ResponseStatus.PARKING_LOT_NOT_AVAILABLE));
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

    @Transactional(readOnly = true)
    @Override
    public ReservationResponseDto getReservation(ReservationGetRequestDto reservationGetRequestDto) {
        Reservation reservation = reservationRepository.findByReservationCodeAndUserUuid(
                        reservationGetRequestDto.getReservationCode(),
                        reservationGetRequestDto.getUserUuid())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ReservationResponseDto.from(reservation);
    }
}
