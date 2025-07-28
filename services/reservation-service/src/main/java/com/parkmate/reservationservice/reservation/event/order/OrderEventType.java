package com.parkmate.reservationservice.reservation.event.order;

import lombok.Getter;

@Getter
public enum OrderEventType {

    COMPLETED("주문 완료"),
    CANCELLED("주문 취소")
    ;

    private final String description;

    OrderEventType(String description) {
        this.description = description;
    }
}
