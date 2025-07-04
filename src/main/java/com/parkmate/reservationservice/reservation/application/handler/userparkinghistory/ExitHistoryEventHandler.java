package com.parkmate.reservationservice.reservation.application.handler.userparkinghistory;

import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.event.userparkinghistory.HistoryType;
import com.parkmate.reservationservice.reservation.event.userparkinghistory.UserParkingHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExitHistoryEventHandler implements EventHandler<UserParkingHistoryEvent> {

    private final ReservationService reservationService;

    @Override
    public boolean supports(Object event) {
        return event instanceof UserParkingHistoryEvent &&
                ((UserParkingHistoryEvent) event).getType() == HistoryType.EXIT;
    }

    @Override
    public void handle(UserParkingHistoryEvent event) {
        reservationService.changeStatus(event.getReservationCode(), ReservationStatus.COMPLETED);
    }
}
