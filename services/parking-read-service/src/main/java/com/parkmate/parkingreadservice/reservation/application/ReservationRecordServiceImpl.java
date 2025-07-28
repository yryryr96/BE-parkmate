package com.parkmate.parkingreadservice.reservation.application;

import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.exception.ResponseStatus;
import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEvent;
import com.parkmate.parkingreadservice.reservation.domain.ReservationRecord;
import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;
import com.parkmate.parkingreadservice.reservation.infrastructure.ReservationRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationRecordServiceImpl implements ReservationRecordService {

    private final ReservationRecordRepository reservationRecordRepository;

    @Async
    @Override
    public void create(ReservationEvent event) {

        reservationRecordRepository.findByReservationCode(event.getReservationCode()).ifPresentOrElse(
                reservationRecord -> {
                    reservationRecord = event.toRecord(reservationRecord.getId());
                    reservationRecordRepository.save(reservationRecord);
                },
                () -> {
                    reservationRecordRepository.save(event.toRecord());
                }
        );
    }

    @Async
    @Override
    public void update(ReservationEvent event) {

        ReservationRecord reservation = reservationRecordRepository.findByReservationCode(event.getReservationCode())
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        reservation = event.toRecord(reservation.getId());
        reservationRecordRepository.save(reservation);
    }

    @Override
    public List<ReserveParkingLotResponseDto> getParkingLotUuidsByUuidsAndDates(List<String> parkingLotUuids,
                                                                                LocalDateTime startDateTime,
                                                                                LocalDateTime endDateTime) {

        return reservationRecordRepository.findAllByParkingLotUuidsAndDates(
                parkingLotUuids,
                startDateTime,
                endDateTime
        );
    }
}
