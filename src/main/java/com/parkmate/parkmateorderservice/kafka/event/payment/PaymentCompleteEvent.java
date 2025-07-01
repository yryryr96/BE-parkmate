package com.parkmate.parkmateorderservice.kafka.event.payment;

import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.Getter;

@Getter
public class PaymentCompleteEvent {

    private String paymentCode;
    private PaymentType paymentType;
    private String orderCode;
    private String amount;
}
