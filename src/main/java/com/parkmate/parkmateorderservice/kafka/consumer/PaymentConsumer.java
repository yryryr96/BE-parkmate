package com.parkmate.parkmateorderservice.kafka.consumer;

import com.parkmate.parkmateorderservice.kafka.constant.KafkaTopics;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEvent;
import com.parkmate.parkmateorderservice.order.application.dispatcher.EventDispatcher;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_TOPIC,
            containerFactory = "paymentEventContainerFactory"
    )
    public void consumePaymentCompletedEvent(PaymentEvent event) {
        EventHandler dispatcher = eventDispatcher.dispatch(event);
        dispatcher.handle(event);
    }
}
