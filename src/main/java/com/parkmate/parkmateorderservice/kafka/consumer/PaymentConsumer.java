package com.parkmate.parkmateorderservice.kafka.consumer;

import com.parkmate.parkmateorderservice.kafka.constant.KafkaTopics;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentCompleteEvent;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final OrderService orderService;
    private static final

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_COMPLETED,
            containerFactory = "paymentCompleteEventContainerFactory"
    )
    public void consumePaymentCompletedEvent(PaymentCompleteEvent event) {
        orderService.changeStatus(event.getOrderCode(), OrderStatus.PAID);
    }
}
