package com.parkmate.reservationservice.reservation.event.order;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderEvent {

    private OrderEventType eventType;
    private String reservationCode;
    private long amount;
}
