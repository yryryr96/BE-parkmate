package com.parkmate.reservationservice.kafka.consumer.order;

import com.parkmate.reservationservice.kafka.constant.KafkaConsumerGroups;
import com.parkmate.reservationservice.kafka.constant.KafkaTopics;
import com.parkmate.reservationservice.reservation.application.dispatcher.EventDispatcher;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import com.parkmate.reservationservice.reservation.event.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(
            topics = KafkaTopics.ORDER_TOPIC,
            containerFactory = "orderContainerFactory"
    )
    public void consume(OrderEvent event) {

        EventHandler eventHandler = eventDispatcher.dispatch(event);
        eventHandler.handle(event);
    }
}
