package com.parkmate.parkmateorderservice.kafka.event.order;

import lombok.Getter;

@Getter
public enum OrderEventType {

    COMPLETE("주문 완료"),
    CANCEL("주문 취소")
    ;

    private final String description;

    OrderEventType(String description) {
        this.description = description;
    }
}
