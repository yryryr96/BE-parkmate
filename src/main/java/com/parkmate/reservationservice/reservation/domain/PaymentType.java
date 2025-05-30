package com.parkmate.reservationservice.reservation.domain;

import lombok.Getter;

@Getter
public enum PaymentType {
    PG("PG 결제"),
    POINT("포인트 결제"),
    ;

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }
}
