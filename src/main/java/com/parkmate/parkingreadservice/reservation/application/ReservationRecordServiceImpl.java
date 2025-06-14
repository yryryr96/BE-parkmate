package com.parkmate.parkingreadservice.reservation.application;

import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;
import com.parkmate.parkingreadservice.reservation.infrastructure.ReservationRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationRecordServiceImpl implements ReservationRecordService {

    private final ReservationRecordRepository reservationRecordRepository;

    @Async
    @Override
    public void create(ReservationCreateEvent reservationCreateEvent) {
        reservationRecordRepository.save(reservationCreateEvent.toRecord());
    }
}
