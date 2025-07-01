package com.parkmate.reservationservice.reservation.application.handler.order;

import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.event.order.OrderEvent;
import com.parkmate.reservationservice.reservation.event.order.OrderEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelEventHandler implements EventHandler<OrderEvent> {

    private final ReservationService reservationService;

    @Override
    public boolean supports(OrderEvent event) {
        return event.getType() == OrderEventType.CANCELLED;
    }

    @Override
    public void handle(OrderEvent event) {
        reservationService.changeStatus(event.getReservationCode(), ReservationStatus.CANCELLED);
    }
}
