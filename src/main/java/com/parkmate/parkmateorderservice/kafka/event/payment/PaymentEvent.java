package com.parkmate.parkmateorderservice.kafka.event.payment;

import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.Getter;

@Getter
public class PaymentEvent {

    private PaymentEventType eventType;
    private String paymentCode;
    private PaymentType paymentType;
    private String orderCode;
    private long amount;
}
