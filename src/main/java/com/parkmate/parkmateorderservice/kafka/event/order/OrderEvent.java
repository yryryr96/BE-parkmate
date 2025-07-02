package com.parkmate.parkmateorderservice.kafka.event.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderEvent {

    private OrderEventType eventType;
    private String orderCode;
    private String paymentCode;
    private String reservationCode;
    private long amount;

    @Builder
    private OrderEvent(OrderEventType eventType,
                       String orderCode,
                       String paymentCode,
                       String reservationCode,
                       long amount) {

        this.eventType = eventType;
        this.orderCode = orderCode;
        this.paymentCode = paymentCode;
        this.reservationCode = reservationCode;
        this.amount = amount;
    }
}
