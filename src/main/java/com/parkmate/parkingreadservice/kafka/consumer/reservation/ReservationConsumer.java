package com.parkmate.parkingreadservice.kafka.consumer.reservation;

import com.parkmate.parkingreadservice.kafka.constant.KafkaTopics;
import com.parkmate.parkingreadservice.kafka.dispatcher.EventDispatcher;
import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEvent;
import com.parkmate.parkingreadservice.kafka.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationConsumer {

    private final EventDispatcher dispatcher;

    @KafkaListener(
            topics = KafkaTopics.RESERVATION,
            containerFactory = "reservationCreateKafkaListener"
    )
    public void consumeReservationCreatedEvent(ConsumerRecord<String, ReservationEvent> record) {

        long offset = record.offset();
        int partition = record.partition();
        ReservationEvent event = record.value();

        try {
            EventHandler eventHandler = dispatcher.dispatch(event);
            eventHandler.handle(event);
        } catch (Exception e) {
            log.error("[Consume Error] offset {} in partition {}: {}", offset, partition, e.getMessage(), e);
        }


    }
}
