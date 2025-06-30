package com.parkmate.parkmateorderservice.order.domain;

import lombok.Getter;

@Getter
public enum PaymentType {

    POINT("포인트"),
    PG("PG 결제")
    ;

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }
}
