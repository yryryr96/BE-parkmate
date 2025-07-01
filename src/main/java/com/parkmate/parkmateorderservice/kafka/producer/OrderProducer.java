package com.parkmate.parkmateorderservice.kafka.producer;

import com.parkmate.parkmateorderservice.kafka.constant.KafkaTopics;
import com.parkmate.parkmateorderservice.kafka.event.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(OrderEvent orderEvent) {
        kafkaTemplate.send(KafkaTopics.ORDER_TOPIC, orderEvent.getOrderCode(), orderEvent);
    }
}
