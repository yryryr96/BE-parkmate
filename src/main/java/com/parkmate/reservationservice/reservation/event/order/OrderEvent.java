package com.parkmate.reservationservice.reservation.event.order;

import lombok.Getter;

@Getter
public class OrderEvent {

    private OrderEventType type;
    private String reservationCode;
    private double amount;
}
