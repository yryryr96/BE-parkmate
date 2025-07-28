package com.parkmate.parkmateorderservice.kafka.consumer;

import com.parkmate.parkmateorderservice.kafka.constant.KafkaTopics;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEvent;
import com.parkmate.parkmateorderservice.order.application.dispatcher.EventDispatcher;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_TOPIC,
            containerFactory = "paymentEventContainerFactory"
    )
    public void consumePaymentCompletedEvent(ConsumerRecord<String, PaymentEvent> record) {

        long offset = record.offset();
        int partition = record.partition();
        PaymentEvent event = record.value();

        try {
            EventHandler dispatcher = eventDispatcher.dispatch(event);
            dispatcher.handle(event);
        } catch (Exception e) {
            log.error("[Consume Error] offset {} in partition {}: {}", offset, partition, e.getMessage(), e);
        }

    }
}
