package com.parkmate.parkingreadservice.kafka.eventmanager;

import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;
import com.parkmate.parkingreadservice.reservation.application.ReservationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEventManager {

    private final ReservationRecordService reservationRecordService;

    public void handleReservationCreatedEvent(ReservationCreateEvent event) {
        reservationRecordService.create(event);
    }
}
