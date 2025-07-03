package com.parkmate.parkmateorderservice.kafka.event.payment;

import lombok.Getter;

@Getter
public class PaymentEvent {

    private PaymentEventType eventType;
    private String paymentCode;
    private String orderCode;
    private long amount;
}
