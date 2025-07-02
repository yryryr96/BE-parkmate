package com.parkmate.parkmateorderservice.order.domain;

import lombok.Getter;

@Getter
public enum OrderType {

    RESERVATION("예약"),
    POINT_CHARGE("포인트 충전")
    ;

    private final String description;

    OrderType(String description) {
        this.description = description;
    }
}
