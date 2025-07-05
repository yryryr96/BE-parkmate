package com.parkmate.parkingreadservice.kafka.handler.reservation;

import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEvent;
import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEventType;
import com.parkmate.parkingreadservice.kafka.handler.EventHandler;
import com.parkmate.parkingreadservice.reservation.application.ReservationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreateEventHandler implements EventHandler<ReservationEvent> {

    private final ReservationRecordService reservationRecordService;

    @Override
    public boolean supports(Object event) {
        return event instanceof ReservationEvent && ((ReservationEvent) event).getEventType() == ReservationEventType.CREATED;
    }

    @Override
    public void handle(ReservationEvent event) {
        reservationRecordService.create(event);
    }
}
