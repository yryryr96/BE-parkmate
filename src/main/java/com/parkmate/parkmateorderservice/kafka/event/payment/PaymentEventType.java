package com.parkmate.parkmateorderservice.kafka.event.payment;

import lombok.Getter;

@Getter
public enum PaymentEventType {

    COMPLETED("결제 완료"),
    FAILED("결제 실패"),
    REFUNDED("결제 환불"),
    CANCELLED("결제 취소")
    ;

    private final String description;

    PaymentEventType(String description) {
        this.description = description;
    }
}
