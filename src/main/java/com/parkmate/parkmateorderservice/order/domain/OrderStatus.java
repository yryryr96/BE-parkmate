package com.parkmate.parkmateorderservice.order.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING("대기중"),
    PAID("결제완료"),
    PAYMENT_FAILED("결제 시도 실패"),
    CANCELLED("취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
